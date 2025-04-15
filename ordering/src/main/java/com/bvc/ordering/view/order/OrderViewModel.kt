package com.bvc.ordering.view.order

import com.bvc.domain.log
import com.bvc.domain.model.CategoryEntity
import com.bvc.domain.model.ProductEntity
import com.bvc.domain.model.SubCategoryEntity
import com.bvc.domain.repository.ProductStoreRepository
import com.bvc.domain.usecase.MainUseCase
import com.bvc.domain.usecase.PreferenceUseCase
import com.bvc.ordering.base.BaseViewModel
import com.bvc.ordering.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class OrderViewModel
    @Inject
    constructor(
        private val preferenceUseCase: PreferenceUseCase,
        private val mainUseCase: MainUseCase,
        private val cartStoreRepository: ProductStoreRepository,
    ) : BaseViewModel() {
        private val _category = MutableStateFlow<List<CategoryEntity>>(emptyList())
        val category: StateFlow<List<CategoryEntity>> get() = _category

        private val _subCategory = MutableStateFlow<List<SubCategoryEntity>>(emptyList())
        val subCategory: StateFlow<List<SubCategoryEntity>> get() = _subCategory

        private val _product = MutableStateFlow<List<ProductEntity>>(emptyList())
        val product: StateFlow<List<ProductEntity>> get() = _product

        private val _cartData = MutableStateFlow<List<ProductEntity>>(emptyList())
        val cartData: StateFlow<List<ProductEntity>> get() = _cartData

        init {
            getCategory()
        }

        private fun getCategory() {
            requestApi(
                request = {
                    mainUseCase.getMenuCategory(
                        this@OrderViewModel,
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
                        this@OrderViewModel,
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
                        this@OrderViewModel,
                        token = preferenceUseCase.getToken(),
                        storeId = "${preferenceUseCase.getStoreId()}",
                        mainCategoryId = "${subCategoryEntity?.mainCategoryId}",
                        subCategoryId = "${subCategoryEntity?.subCategoryId}",
                    )
                },
                successAction = { response ->
                    log.e("response: $response")
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
