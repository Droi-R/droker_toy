package com.bvc.ordering.view.table

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bvc.domain.log
import com.bvc.domain.model.CategoryEntity
import com.bvc.domain.model.ProductEntity
import com.bvc.domain.model.SubCategoryEntity
import com.bvc.domain.model.TableEntity
import com.bvc.domain.repository.TableStoreRepository
import com.bvc.domain.type.OrderFrom
import com.bvc.domain.type.OrderStatus
import com.bvc.domain.type.PaymentStatus
import com.bvc.domain.type.ScreenState
import com.bvc.domain.usecase.MainUseCase
import com.bvc.domain.usecase.PreferenceUseCase
import com.bvc.ordering.base.BaseViewModel
import com.bvc.ordering.base.SingleLiveEvent
import com.bvc.ordering.util.Utils
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
        private val mainUseCase: MainUseCase,
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
                    mainUseCase.postOrder(
                        this@TableOrderViewModel,
                        preferenceUseCase.getToken(),
                        id = "",
                        productItems = cartData.value,
                        orderStatus = OrderStatus.PENDING,
                        paymentStatus = PaymentStatus.READY,
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
            requestApi(
                request = {
                    mainUseCase.getMenuCategory(
                        this@TableOrderViewModel,
                        preferenceUseCase.getToken(),
                        "${preferenceUseCase.getStoreId()}",
                    )
                },
                successAction = { response ->
                    val selectId = category.value.find { it.selected }?.mainCategoryId
                    _category.value =
                        (
                            response.data?.map {
                                CategoryEntity(
                                    mainCategoryId = it.mainCategoryId,
                                    name = it.name,
                                    selected = selectId == it.mainCategoryId,
                                )
                            }
                        )?.let { list ->
                            // selected 없으면 첫번째 선택 처리
                            if (list.none { it.selected } && list.isNotEmpty()) {
                                list.mapIndexed { index, item ->
                                    item.copy(selected = index == 0)
                                }
                            } else {
                                list
                            }
                        } ?: emptyList()
                },
                errorAction = { code, message ->
                    log.e("code: $code, message: $message")
                    Utils.showToast(message)
                },
            )
        }

        fun getSubCategory(mainCategoryId: String) {
            requestApi(
                request = {
                    mainUseCase.getSubCategory(
                        this@TableOrderViewModel,
                        token = preferenceUseCase.getToken(),
                        storeId = "${preferenceUseCase.getStoreId()}",
                        mainCategoryId = mainCategoryId,
                    )
                },
                successAction = { response ->
                    val selectId = subCategory.value.find { it.selected }?.subCategoryId
                    _subCategory.value =
                        (
                            response.data?.map {
                                SubCategoryEntity(
                                    mainCategoryId = it.mainCategoryId,
                                    storeId = it.storeId,
                                    subCategoryId = it.subCategoryId,
                                    name = it.name,
                                    selected = selectId == it.subCategoryId,
                                )
                            }
                        )?.let { list ->
                            // selected 없으면 첫번째 선택 처리
                            if (list.none { it.selected } && list.isNotEmpty()) {
                                list.mapIndexed { index, item ->
                                    item.copy(selected = index == 0)
                                }
                            } else {
                                list
                            }
                        } ?: emptyList()
                },
                errorAction = { code, message ->
                    log.e("code: $code, message: $message")
                    Utils.showToast(message)
                },
            )
        }

        fun getProducts(subCategoryEntity: SubCategoryEntity?) {
            requestApi(
                request = {
                    mainUseCase.getProducts(
                        this@TableOrderViewModel,
                        token = preferenceUseCase.getToken(),
                        storeId = "${preferenceUseCase.getStoreId()}",
                        mainCategoryId = "${subCategoryEntity?.mainCategoryId}",
                        subCategoryId = "${subCategoryEntity?.subCategoryId}",
                    )
                },
                successAction = { response ->
//                    _product.value =
//                        (
//                            buildList {
//                                addAll(response.data.orEmpty().map { it.copy() })
//                            }
//                        )
                    _product.value = response.data.orEmpty().toList()
                },
                errorAction = { code, message ->
                    log.e("code: $code, message: $message")
                    Utils.showToast(message)
                },
            )
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
