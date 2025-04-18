package com.bvc.ordering.view.table

import androidx.lifecycle.viewModelScope
import com.bvc.domain.log
import com.bvc.domain.model.CategoryEntity
import com.bvc.domain.model.OrderEntity
import com.bvc.domain.model.OrderMemo
import com.bvc.domain.model.ProductEntity
import com.bvc.domain.model.SubCategoryEntity
import com.bvc.domain.model.TableEntity
import com.bvc.domain.type.OrderFrom
import com.bvc.domain.type.OrderStatus
import com.bvc.domain.type.PaymentStatus
import com.bvc.domain.type.ScreenState
import com.bvc.domain.usecase.MainUseCase
import com.bvc.domain.usecase.PreferenceUseCase
import com.bvc.ordering.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TableViewModel
    @Inject
    constructor(
        private val preferenceUseCase: PreferenceUseCase,
        private val getMainUseCase: MainUseCase,
    ) : BaseViewModel() {
        private val _category = MutableStateFlow<List<CategoryEntity>>(emptyList())
        val category: StateFlow<List<CategoryEntity>> get() = _category

        private val _subCategory = MutableStateFlow<List<SubCategoryEntity>>(emptyList())
        val subCategory: StateFlow<List<SubCategoryEntity>> get() = _subCategory

        private val _tables = MutableStateFlow<List<TableEntity>>(emptyList())
        val tables: StateFlow<List<TableEntity>> get() = _tables

//        private val _cartData = MutableStateFlow<List<CartEntity>>(emptyList())
//        val cartData: StateFlow<List<CartEntity>> get() = _cartData

        init {
            getCategory()
        }

        private fun getCategory() {
            viewModelScope.launch {
                val response =
                    getMainUseCase.getMenuCategory(
                        token = preferenceUseCase.getToken(),
                        storeId = "${preferenceUseCase.getStoreId()}",
                    )
                val selectId = category.value?.find { it.selected }?.mainCategoryId
                _category.value =
                    (
                        listOf(
                            CategoryEntity(mainCategoryId = "1", name = "홀", selected = false),
                            CategoryEntity(mainCategoryId = "2", name = "2층", selected = false),
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
                        token = preferenceUseCase.getToken(),
                        storeId = "${preferenceUseCase.getStoreId()}",
                        mainCategoryId = id,
                    )
                //                val selectId = subCategory.value?.find { it.selected }?.id
                _subCategory.value = (
//                    response?.data?.map {
//                        SubCategoryEntity(
//                            subCategoryId = it.subCategoryId,
//                            name = it.name,
//                            selected = false,
//                        )
//                    } ?:
                    listOf(
                        SubCategoryEntity(
                            subCategoryId = "1",
                            name = "결합결제",
                            selected = false,
                        ),
                        SubCategoryEntity(
                            subCategoryId = "2",
                            name = "이동/합석",
                            selected = false,
                        ),
                        SubCategoryEntity(
                            subCategoryId = "3",
                            name = "좌석정보",
                            selected = false,
                        ),
                        SubCategoryEntity(
                            subCategoryId = "4",
                            name = "예약",
                            selected = false,
                        ),
                    )
                )
                //                log.e("_subCategory: ${subCategory.value}")
                //                val selectedCategory = subCategory.value?.find { it.selected }
                //                if (selectedCategory == null && subCategory.value?.isNotEmpty() == true) {
                //                    _subCategory.value =
                //                        subCategory.value.mapIndexed { index, item ->
                //                            if (index == 0) item.copy(selected = true) else item.copy(selected = false)
                //                        }
                //                }

                if (response == null) {
                    mutableScreenState.postValue(ScreenState.ERROR)
                } else {
                    mutableScreenState.postValue(ScreenState.RENDER)
                }
            }
        }

        fun getTables(externalKey: String) {
            viewModelScope.launch {
                val response =
                    getMainUseCase.getTables(preferenceUseCase.getToken(), externalKey)
                _tables.value = (
                    //                    response.data ?:
                    listOf(
                        TableEntity(
                            tableExternalKey = "1",
                            tableName = "1번 테이블",
                            tableNumber = 1,
                            orders = arrayListOf(),
                        ),
                        TableEntity(
                            tableExternalKey = "2",
                            tableName = "2번 테이블",
                            tableNumber = 2,
                            orders =
                                arrayListOf(
                                    OrderEntity(
                                        orderID = "1",
                                        orderName = "테스트 주문",
                                        orderItems =
                                            arrayListOf(
                                                ProductEntity(
                                                    name = "콜라",
                                                    mainCategoryId = "C001",
                                                    descriptions = "시원한 콜라",
                                                    isVat = true,
                                                    selected = false,
                                                    stock = 0,
                                                    color = "#000000",
                                                    imageUrl = "https://example.com/images/coke.png",
                                                    basePrice = "1500",
                                                    optionGroups = emptyList(),
                                                    position = 1,
                                                    quantity = 1,
                                                    isSoldOut = false,
                                                    isVatIncluded = true,
                                                    barcode = "1234567890123",
                                                    dailyLimit = 10,
                                                    useStock = true,
                                                    productId = "P001",
                                                    storeId = "S001",
                                                    subCategoryId = "SC001",
                                                    productRecipes = emptyList(),
                                                ),
                                            ),
                                        buyerName = "홍길동",
                                        orderFrom = OrderFrom.POS,
                                        orderStatus = OrderStatus.PENDING,
                                        paymentStatus = PaymentStatus.READY,
                                        tableNumber = 2,
                                        buyerPhoneNumber = "010-1234-5678",
                                        additionalComment = "빨리 부탁드립니다.",
                                        orderNumber = 12345,
                                        orderMemos =
                                            arrayListOf(
                                                OrderMemo(
                                                    externalKey = "memo1",
                                                    oid = "1",
                                                    content = "추가 소스 요청",
                                                    createdAt = "2023-01-01T12:00:00",
                                                    updatedAt = "2023-01-01T12:30:00",
                                                ),
                                            ),
                                    ),
                                ),
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
            log.e("updateCategory: $item")
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
    }
