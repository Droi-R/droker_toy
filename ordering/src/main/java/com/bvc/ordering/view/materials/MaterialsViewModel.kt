package com.bvc.ordering.view.materials

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bvc.domain.log
import com.bvc.domain.model.CategoryEntity
import com.bvc.domain.model.MaterialsEntity
import com.bvc.domain.model.Options
import com.bvc.domain.model.ProductEntity
import com.bvc.domain.model.ProductOptionEntity
import com.bvc.domain.model.SmartOrderEntity
import com.bvc.domain.model.Stock
import com.bvc.domain.model.SubCategoryEntity
import com.bvc.domain.usecase.MainUseCase
import com.bvc.domain.usecase.PreferenceUseCase
import com.bvc.ordering.R
import com.bvc.ordering.base.BaseViewModel
import com.bvc.ordering.util.ResourceProvider
import com.bvc.ordering.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MaterialsViewModel
    @Inject
    constructor(
        private val preferenceUseCase: PreferenceUseCase,
        private val mainUseCase: MainUseCase,
        private val resourceProvider: ResourceProvider,
    ) : BaseViewModel() {
        private val _topCategory = MutableStateFlow<List<SubCategoryEntity>>(emptyList())
        val topCategory: StateFlow<List<SubCategoryEntity>> get() = _topCategory

        private val _category = MutableStateFlow<List<CategoryEntity>>(emptyList())
        val category: StateFlow<List<CategoryEntity>> get() = _category

        private val _subCategory = MutableStateFlow<List<SubCategoryEntity>>(emptyList())
        val subCategory: StateFlow<List<SubCategoryEntity>> get() = _subCategory

        private val _material = MutableStateFlow<List<MaterialsEntity>>(emptyList())
        val material: StateFlow<List<MaterialsEntity>> get() = _material

        private val _smartOrder = MutableStateFlow<List<SmartOrderEntity>>(emptyList())
        val smartOrder: StateFlow<List<SmartOrderEntity>> get() = _smartOrder

        private val _product = MutableStateFlow<List<ProductEntity>>(emptyList())
        val product: StateFlow<List<ProductEntity>> get() = _product

        private val _smartOrderSelect = MutableStateFlow<Boolean>(true)
        val smartOrderSelect: StateFlow<Boolean> get() = _smartOrderSelect

        private val _visibleSmartOrder =
            MutableLiveData<Boolean>().apply {
                value = false
            }
        val visibleSmartOrder: LiveData<Boolean> get() = _visibleSmartOrder

        private val _smartOrderDate = MutableLiveData<String>()
        val smartOrderDate: LiveData<String> get() = _smartOrderDate

        init {
            getTopCategory()
//            getCategory("")
            _smartOrderDate.value =
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                    Date(),
                )
        }

        private fun getTopCategory() {
            val selectId = topCategory.value.find { it.selected }?.subCategoryId
            _topCategory.value =
                (
                    listOf(
                        SubCategoryEntity(
                            subCategoryId = "1",
                            name = resourceProvider.getString(R.string.materials_status),
                            selected = selectId == "1",
                        ),
                        SubCategoryEntity(
                            subCategoryId = "2",
                            name = resourceProvider.getString(R.string.materials_smart_order),
                            selected = selectId == "2",
                        ),
                        SubCategoryEntity(
                            subCategoryId = "3",
                            name = resourceProvider.getString(R.string.materials_consumption),
                            selected = selectId == "3",
                        ),
                        SubCategoryEntity(
                            subCategoryId = "4",
                            name = resourceProvider.getString(R.string.materials_order_status),
                            selected = selectId == "4",
                        ),
                    )
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
        }

        fun getCategory(mainCategoryId: String) {
            requestApi(
                request = {
                    mainUseCase.getMenuCategory(
                        this@MaterialsViewModel,
                        token = preferenceUseCase.getToken(),
                        storeId = "${preferenceUseCase.getStoreId()}",
//                        mainCategoryId = mainCategoryId,
                    )
                },
                successAction = { response ->
                    val selectId = category.value.find { it.selected }?.mainCategoryId
                    _category.value = listOf(CategoryEntity()) // test용
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
                        this@MaterialsViewModel,
                        token = preferenceUseCase.getToken(),
                        storeId = "${preferenceUseCase.getStoreId()}",
                        mainCategoryId = mainCategoryId,
                    )
                },
                successAction = { response ->
                    val selectId = subCategory.value.find { it.selected }?.subCategoryId
                    _subCategory.value =
                        (
                            //                            response.data?.map {
                            //                                SubCategoryEntity(
                            //                                    mainCategoryId = it.mainCategoryId,
                            //                                    storeId = it.storeId,
                            //                                    subCategoryId = it.subCategoryId,
                            //                                    name = it.name,
                            //                                    selected = selectId == it.subCategoryId,
                            //                                )
                            //                            }
                            listOf(
                                SubCategoryEntity(
                                    mainCategoryId = "1",
                                    storeId = "1",
                                    subCategoryId = "1",
                                    name = "전체",
                                    selected = false,
                                ),
                                SubCategoryEntity(
                                    mainCategoryId = "2",
                                    storeId = "2",
                                    subCategoryId = "2",
                                    name = "냉동 및 고기",
                                    selected = false,
                                ),
                                SubCategoryEntity(
                                    mainCategoryId = "3",
                                    storeId = "3",
                                    subCategoryId = "3",
                                    name = "유제품",
                                    selected = false,
                                ),
                                SubCategoryEntity(
                                    mainCategoryId = "4",
                                    storeId = "4",
                                    subCategoryId = "4",
                                    name = "청과류",
                                    selected = false,
                                ),
                                SubCategoryEntity(
                                    mainCategoryId = "5",
                                    storeId = "5",
                                    subCategoryId = "5",
                                    name = "건재료",
                                    selected = false,
                                ),
                                SubCategoryEntity(
                                    mainCategoryId = "6",
                                    storeId = "6",
                                    subCategoryId = "6",
                                    name = "공산품",
                                    selected = false,
                                ),
                                SubCategoryEntity(
                                    mainCategoryId = "7",
                                    storeId = "7",
                                    subCategoryId = "7",
                                    name = "기타",
                                    selected = false,
                                ),
                            )
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
                    _subCategory.value =
                        (
                            listOf(
                                SubCategoryEntity(
                                    mainCategoryId = "1",
                                    storeId = "1",
                                    subCategoryId = "1",
                                    name = "전체",
                                    selected = false,
                                ),
                                SubCategoryEntity(
                                    mainCategoryId = "2",
                                    storeId = "2",
                                    subCategoryId = "2",
                                    name = "냉동 및 고기",
                                    selected = false,
                                ),
//                                SubCategoryEntity(
//                                    mainCategoryId = "3",
//                                    storeId = "3",
//                                    subCategoryId = "3",
//                                    name = "유제품",
//                                    selected = false,
//                                ),
//                                SubCategoryEntity(
//                                    mainCategoryId = "4",
//                                    storeId = "4",
//                                    subCategoryId = "4",
//                                    name = "청과류",
//                                    selected = false,
//                                ),
//                                SubCategoryEntity(
//                                    mainCategoryId = "5",
//                                    storeId = "5",
//                                    subCategoryId = "5",
//                                    name = "건재료",
//                                    selected = false,
//                                ),
                                SubCategoryEntity(
                                    mainCategoryId = "6",
                                    storeId = "6",
                                    subCategoryId = "6",
                                    name = "공산품",
                                    selected = false,
                                ),
                                SubCategoryEntity(
                                    mainCategoryId = "7",
                                    storeId = "7",
                                    subCategoryId = "7",
                                    name = "기타",
                                    selected = false,
                                ),
                            )
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
            )
        }

        fun getMaterials(mainCategoryId: String) {
            requestApi(
                request = {
                    mainUseCase.getMaterials(
                        this@MaterialsViewModel,
                        token = preferenceUseCase.getToken(),
                        storeId = "${preferenceUseCase.getStoreId()}",
                        mainCategoryId = "$mainCategoryId",
                        subCategoryId = "$mainCategoryId",
                    )
                },
                successAction = { response ->
//                    _material.value = response.data.orEmpty().toList()
                    _material.value =
                        listOf(
                            MaterialsEntity(
                                materialId = "1",
                                materialName = "재료1",
                                stock = 10,
                                safetyStock = 15,
                                imageUrl = "",
                                unit = "",
                            ),
                            MaterialsEntity(
                                materialId = "2",
                                materialName = "재료2",
                                stock = 20,
                                safetyStock = 30,
                                imageUrl = "",
                                unit = "",
                            ),
                            MaterialsEntity(
                                materialId = "3",
                                materialName = "재료2",
                                stock = 30,
                                safetyStock = 55,
                                imageUrl = "",
                                unit = "",
                            ),
                        )
                },
                errorAction = { code, message ->
                    _material.value =
                        listOf(
                            MaterialsEntity.EMPTY,
                        )
                    Utils.showToast(message)
                    _material.value =
                        listOf(
                            MaterialsEntity(
                                materialId = "1",
                                materialName = "재료1",
                                stock = 10,
                                safetyStock = 15,
                                imageUrl = "",
                                unit = "",
                            ),
                            MaterialsEntity(
                                materialId = "2",
                                materialName = "재료2",
                                stock = 20,
                                safetyStock = 30,
                                imageUrl = "",
                                unit = "",
                            ),
                            MaterialsEntity(
                                materialId = "3",
                                materialName = "재료3",
                                stock = 30,
                                safetyStock = 55,
                                imageUrl = "",
                                unit = "",
                            ),
                            MaterialsEntity(
                                materialId = "4",
                                materialName = "재료4",
                                stock = 40,
                                safetyStock = 60,
                                imageUrl = "",
                                unit = "",
                            ),
                            MaterialsEntity(
                                materialId = "5",
                                materialName = "재료5",
                                stock = 50,
                                safetyStock = 70,
                                imageUrl = "",
                                unit = "",
                            ),
                            MaterialsEntity(
                                materialId = "6",
                                materialName = "재료6",
                                stock = 60,
                                safetyStock = 80,
                                imageUrl = "",
                                unit = "",
                            ),
                            MaterialsEntity(
                                materialId = "7",
                                materialName = "재료7",
                                stock = 70,
                                safetyStock = 90,
                                imageUrl = "",
                                unit = "",
                            ),
                        )
                },
            )
        }

        fun getProducts(subCategoryEntity: SubCategoryEntity?) {
            requestApi(
                request = {
                    mainUseCase.getProducts(
                        this@MaterialsViewModel,
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
                    _product.value =
                        listOf(
                            ProductEntity(
                                externalKey = "12345",
                                productId = "P001",
                                storeId = "S001",
                                mainCategoryId = "C001",
                                subCategoryId = "SC001",
                                name = "테스트 상품",
                                descriptions = "테스트 상품 설명",
                                isVat = true,
                                selected = false,
                                stock =
                                    Stock(
                                        externalKey = "ST001",
                                        useStock = true,
                                        count = 100,
                                    ),
                                color = "#FF5733",
                                useStock = true,
                                optionGroups =
                                    listOf(
                                        ProductOptionEntity(
                                            optionGroupId = "OG001",
                                            name = "옵션 그룹 1",
                                            required = false,
                                            minOptionCountLimit = 1,
                                            maxOptionCountLimit = 3,
                                            position = 1,
                                            options =
                                                arrayListOf(
                                                    Options(
                                                        productOptionsId = "OP001",
                                                        name = "옵션 1",
                                                        price = "1000",
                                                        position = 1,
                                                        useStock = true,
                                                        isSoldOut = false,
                                                        materials = emptyList(),
                                                        isSelected = false,
                                                    ),
                                                ),
                                        ),
                                    ),
                                position = 1,
                                quantity = 1,
                                imageUrl = "https://cdn.pixabay.com/photo/2023/07/07/17/47/sushi-8113165_1280.jpg",
                                basePrice = "10000",
                                isSoldOut = false,
                                isVatIncluded = true,
                                barcode = "1234567890123",
                                dailyLimit = 10,
                            ),
                        )
                },
            )
        }

        private fun getStockIn(smartOrderSelect: Boolean) {
            requestApi(
                request = {
                    mainUseCase.getMenuCategory(
                        this@MaterialsViewModel,
                        token = preferenceUseCase.getToken(),
                        storeId = "${preferenceUseCase.getStoreId()}",
                    )
                },
                successAction = { response ->
                    log.e("smartOrderSelect: $smartOrderSelect      $response")
                },
                errorAction = { code, message ->
                    log.e("code: $code, message: $message")
                    Utils.showToast(message)
                },
            )
        }

        fun getSmartOrder() {
            requestApi(
                request = {
                    mainUseCase.getSmartOrder(
                        this@MaterialsViewModel,
                        token = preferenceUseCase.getToken(),
                        storeId = "${preferenceUseCase.getStoreId()}",
                        mainCategoryId = "${category.value.find { it.selected }?.mainCategoryId}",
                        subCategoryId = "${subCategory.value.find { it.selected }?.subCategoryId}",
                    )
                },
                successAction = { response ->
                    _smartOrder.value = response.data.orEmpty().toList()
                },
                errorAction = { code, message ->
                    log.e("code: $code, message: $message")
                    Utils.showToast(message)
                },
            )
        }

        fun updateTopCategory(item: SubCategoryEntity) {
            _topCategory.value =
                topCategory.value.map {
                    it.copy(selected = it == item) // 새로운 객체 생성
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

        fun selectSmartOrder(select: Boolean) {
            _smartOrderSelect.value = select
            getStockIn(smartOrderSelect.value)
        }

        fun setVisibleSmartOrder(visible: Boolean) {
            _visibleSmartOrder.value = visible
        }
    }
