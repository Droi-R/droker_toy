package com.bvc.ordering.view.table

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bvc.domain.log
import com.bvc.domain.model.CategoryEntity
import com.bvc.domain.model.Options
import com.bvc.domain.model.ProductEntity
import com.bvc.domain.model.ProductOptionEntity
import com.bvc.domain.model.SubCategoryEntity
import com.bvc.domain.model.TableEntity
import com.bvc.domain.repository.TableStoreRepository
import com.bvc.domain.type.OrderFrom
import com.bvc.domain.type.OrderStatus
import com.bvc.domain.type.ScreenState
import com.bvc.domain.usecase.MainUseCase
import com.bvc.domain.usecase.PreferenceUseCase
import com.bvc.ordering.base.BaseViewModel
import com.bvc.ordering.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TableOrderViewModel
    @Inject
    constructor(
        private val preferenceUseCase: PreferenceUseCase,
        private val getMainUseCase: MainUseCase,
        private val cartStoreRepository: TableStoreRepository,
    ) : BaseViewModel() {
        private val _category = MutableStateFlow<List<CategoryEntity>>(emptyList())
        val category: StateFlow<List<CategoryEntity>> get() = _category

        private val _subCategory = MutableStateFlow<List<SubCategoryEntity>>(emptyList())
        val subCategory: StateFlow<List<SubCategoryEntity>> get() = _subCategory

        private val _product = MutableStateFlow<List<ProductEntity>>(emptyList())
        val product: StateFlow<List<ProductEntity>> get() = _product

        private val _cartData = MutableStateFlow<List<ProductEntity>>(emptyList())
        val cartData: StateFlow<List<ProductEntity>> get() = _cartData

        private val _tableInfo = SingleLiveEvent<TableEntity>()
        val tableInfo: LiveData<TableEntity> get() = _tableInfo

        private val _tableName = SingleLiveEvent<String>()
        val tableName: LiveData<String> get() = _tableName

        private val _affiliteName = SingleLiveEvent<String>()
        val affiliteName: LiveData<String> get() = _affiliteName

        init {
            viewModelScope.launch {
                _affiliteName.value = preferenceUseCase.getStoreName() ?: ""
            }
            getCategory()
        }

        fun setTableInfo(table: TableEntity) {
            _tableInfo.value = table
            _tableName.value = table.tableName
            tableInfo.value?.orders?.map {
                it.orderItems.map { item ->
                    cartStoreRepository.addItem(item)
                }
            }
        }

        fun postOrder() {
            requestApi(
                request = {
                    getMainUseCase.postOrder(
                        this@TableOrderViewModel,
                        preferenceUseCase.getToken(),
                        id = "",
                        productItems = cartData.value,
                        status = OrderStatus.READY,
                        orderFrom = OrderFrom.POS,
                        tableNumber = "${tableInfo.value?.tableNumber}",
                        tableExternalKey = tableInfo.value?.tableExternalKey ?: "",
                    )
                },
                successAction = {
                    log.e("success: $it")
                    mutableScreenState.postValue(ScreenState.RENDER)
                },
                errorAction = { code, message ->
                    log.e("error: $message")
                    mutableScreenState.postValue(ScreenState.ERROR)
                },
            )
//            viewModelScope.launch {
//                val response =
//                    getMainUseCase
//                        .postOrder(
//                            remoteErrorEmitter = this@TableOrderViewModel,
//                            token = preferenceUseCase.getToken(),
//                            id = "",
//                            productItems = cartData.value,
//                            status = OrderStatus.READY,
//                            orderFrom = OrderFrom.POS,
//                            tableNumber = "",
//                            tableExternalKey = "",
//                        )
//                log.e("response: ${cartData.value}")
//            }s
        }

        private fun getCategory() {
            viewModelScope.launch {
                val response =
                    getMainUseCase.getMenuCategory(
                        this@TableOrderViewModel,
                        preferenceUseCase.getToken(),
                    )
                val selectId = category.value?.find { it.selected }?.id
                _category.value =
                    (
                        response?.data?.map {
                            CategoryEntity(
                                id = it.id,
                                name = it.name,
                                selected = selectId == it.id,
                            )
                        } ?: listOf(
                            CategoryEntity(
                                id = "1",
                                name = "메뉴",
                                selected = true,
                            ),
                            CategoryEntity(
                                id = "2",
                                name = "배달메뉴",
                                selected = false,
                            ),
                            CategoryEntity(
                                id = "3",
                                name = "포장메뉴",
                                selected = false,
                            ),
                        )
                    ).let { list ->
                        // selected 가 하나도 없으면 첫번째 selected = true
                        if (list.none { it.selected } && list.isNotEmpty()) {
                            list.mapIndexed { index, item ->
                                item.copy(selected = index == 0)
                            }
                        } else {
                            list
                        }
                    }

                if (response == null) {
                    mutableScreenState.postValue(ScreenState.ERROR)
                } else {
                    mutableScreenState.postValue(ScreenState.RENDER)
                }
            }
        }

        fun getSubCategory(id: String) {
            viewModelScope.launch {
                val response =
                    getMainUseCase.getSubCategory(
                        this@TableOrderViewModel,
                        preferenceUseCase.getToken(),
                        id,
                    )
                val selectId = subCategory.value?.find { it.selected }?.id
                _subCategory.value =
                    (
                        response?.data?.map {
                            SubCategoryEntity(
                                id = it.id,
                                name = it.name,
                                selected = selectId == it.id,
                            )
                        } ?: listOf(
                            SubCategoryEntity(id = "1", name = "대표메뉴", selected = false),
                            SubCategoryEntity(id = "2", name = "음식", selected = false),
                            SubCategoryEntity(id = "3", name = "커피", selected = false),
                            SubCategoryEntity(id = "4", name = "디저트", selected = false),
                            SubCategoryEntity(id = "5", name = "주류", selected = false),
                            SubCategoryEntity(id = "6", name = "사이드", selected = false),
                        )
                    ).let { list ->
                        if (list.none { it.selected } && list.isNotEmpty()) {
                            list.mapIndexed { index, item ->
                                item.copy(selected = index == 0)
                            }
                        } else {
                            list
                        }
                    }

                if (response == null) {
                    mutableScreenState.postValue(ScreenState.ERROR)
                } else {
                    mutableScreenState.postValue(ScreenState.RENDER)
                }
            }
        }

        fun getProducts(externalKey: String) {
            viewModelScope.launch {
                val response =
                    getMainUseCase.getProducts(
                        this@TableOrderViewModel,
                        preferenceUseCase.getToken(),
                        externalKey,
                    )
//                log.e("response : ${response.data}")
                _product.value = (
//                    response.data ?:
                    listOf(
                        ProductEntity(
                            externalKey = "1",
                            name = "샌드위치 쿠바노",
                            selected = false,
                            image = "https://cdn.imweb.me/upload/S20210720ef466f4f488bc/04045dccedf91.jpg",
                            price = "8900",
                            isVat = true,
                        ),
                        ProductEntity(
                            externalKey = "2",
                            name = "샌드위치 바게트",
                            selected = false,
                            image = "https://cdn.imweb.me/upload/S20210720ef466f4f488bc/b0773058f7005.jpg",
                            price = "18900",
                            productOption =
                                listOf(
                                    ProductOptionEntity(
                                        id = "1",
                                        name = "바게트",
//                                        selected = false,
//                                        price = "0",
                                        required = "true",
                                        minOptionCountLimit = 1,
                                        maxOptionCountLimit = 1,
                                        position = 0,
                                        options =
                                            arrayListOf(
                                                Options(
                                                    id = "1",
                                                    name = "바게트",
                                                    price = "999",
                                                    position = 0,
                                                    useStock = true,
                                                    stockQuantity = 10,
                                                    isSoldOut = false,
                                                    isSelected = false,
                                                ),
                                                Options(
                                                    id = "2",
                                                    name = "식빵",
                                                    price = "777",
                                                    position = 1,
                                                    useStock = true,
                                                    stockQuantity = 10,
                                                    isSoldOut = false,
                                                    isSelected = false,
                                                ),
                                            ),
                                    ),
                                    ProductOptionEntity(
                                        id = "2",
                                        name = "소스",
//                                        selected = false,
//                                        price = "0",
                                        required = "false",
                                        minOptionCountLimit = 1,
                                        maxOptionCountLimit = 1,
                                        position = 0,
                                        options =
                                            arrayListOf(
                                                Options(
                                                    id = "3",
                                                    name = "마요네즈",
                                                    price = "333",
                                                    position = 0,
                                                    useStock = true,
                                                    stockQuantity = 10,
                                                    isSoldOut = false,
                                                    isSelected = false,
                                                ),
                                                Options(
                                                    id = "4",
                                                    name = "케찹",
                                                    price = "555",
                                                    position = 1,
                                                    useStock = true,
                                                    stockQuantity = 10,
                                                    isSoldOut = false,
                                                    isSelected = false,
                                                ),
                                            ),
                                    ),
                                ),
                        ),
                        ProductEntity(
                            externalKey = "3",
                            name = "샌드위치 과카몰리",
                            selected = false,
                            image = "https://cdn.animaltoc.com/news/photo/202312/505_2403_4626.jpg",
                            price = "10900",
                            isVat = false,
                        ),
                        ProductEntity(
                            externalKey = "4",
                            name = "샌드위치 더블치즈",
                            selected = false,
                            image = "https://cdn.imweb.me/upload/S20210720ef466f4f488bc/b0773058f7005.jpg",
                            price = "10800",
                            isVat = false,
                        ),
                        ProductEntity(
                            externalKey = "5",
                            name = "샌드위치 훈제연어",
                            selected = false,
                            image = "https://cdn.imweb.me/upload/S20210720ef466f4f488bc/b0773058f7005.jpg",
                            price = "10900",
                        ),
                        ProductEntity(
                            externalKey = "6",
                            name = "샌드위치 훈제연어",
                            selected = false,
                            image = "https://cdn.imweb.me/upload/S20210720ef466f4f488bc/b0773058f7005.jpg",
                            price = "10900",
                        ),
                        ProductEntity(
                            externalKey = "7",
                            name = "샌드위치 훈제연어",
                            selected = false,
                            image = "https://cdn.imweb.me/upload/S20210720ef466f4f488bc/b0773058f7005.jpg",
                            price = "10900",
                        ),
                        ProductEntity(
                            externalKey = "8",
                            name = "샌드위치 훈제연어",
                            selected = false,
                            image = "https://cdn.imweb.me/upload/S20210720ef466f4f488bc/b0773058f7005.jpg",
                            price = "10900",
                        ),
                        ProductEntity(
                            externalKey = "9",
                            name = "샌드위치 훈제연어",
                            selected = false,
                            image = "https://cdn.imweb.me/upload/S20210720ef466f4f488bc/b0773058f7005.jpg",
                            price = "10900",
                        ),
                        ProductEntity(
                            externalKey = "10",
                            name = "샌드위치 훈제연어",
                            selected = false,
                            image = "https://cdn.imweb.me/upload/S20210720ef466f4f488bc/b0773058f7005.jpg",
                            price = "10900",
                        ),
                        ProductEntity(
                            externalKey = "11",
                            name = "샌드위치 훈제연어",
                            selected = false,
                            image = "https://cdn.imweb.me/upload/S20210720ef466f4f488bc/b0773058f7005.jpg",
                            price = "10900",
                        ),
                        ProductEntity(
                            externalKey = "12",
                            name = "샌드위치 훈제연어",
                            selected = false,
                            image = "https://cdn.imweb.me/upload/S20210720ef466f4f488bc/b0773058f7005.jpg",
                            price = "10900",
                        ),
                        ProductEntity(
                            externalKey = "13",
                            name = "샌드위치 훈제연어",
                            selected = false,
                            image = "https://cdn.imweb.me/upload/S20210720ef466f4f488bc/b0773058f7005.jpg",
                            price = "10900",
                        ),
                        ProductEntity(
                            externalKey = "14",
                            name = "샌드위치 훈제연어",
                            selected = false,
                            image = "https://cdn.imweb.me/upload/S20210720ef466f4f488bc/b0773058f7005.jpg",
                            price = "10900",
                        ),
                    )
                )
                if (response == null) {
                    mutableScreenState.postValue(ScreenState.ERROR)
                } else {
                    mutableScreenState.postValue(ScreenState.RENDER)
                }
            }
        }

        fun updateCategory(item: CategoryEntity) {
            _category.value =
                category.value.map {
                    it.copy(selected = it == item) // 새로운 객체 생성
                }
        }

        fun updateSubCategory(item: SubCategoryEntity) {
            _subCategory.value =
                subCategory.value.map {
                    it.copy(selected = it == item) // 새로운 객체 생성
                }
        }

        fun updateProduct(item: ProductEntity) {
            _product.value =
                product.value.map {
                    it.copy(selected = it == item) // 새로운 객체 생성
                }
        }

        fun addToCart(item: ProductEntity) {
            cartStoreRepository.addItem(item)
            getCart()
        }

        fun clearCart() {
            cartStoreRepository.clearCart()
            getCart()
        }

        fun getCart() {
            _cartData.value = cartStoreRepository.getItems()
        }
    }
