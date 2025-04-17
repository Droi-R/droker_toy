package com.bvc.ordering.view.materials

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bvc.domain.model.MaterialsEntity
import com.bvc.domain.model.ProductEntity
import com.bvc.domain.usecase.MainUseCase
import com.bvc.domain.usecase.PreferenceUseCase
import com.bvc.ordering.base.BaseViewModel
import com.bvc.ordering.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MaterialDetailViewModel
    @Inject
    constructor(
        private val preferenceUseCase: PreferenceUseCase,
        private val mainUseCase: MainUseCase,
        private val resourceProvider: ResourceProvider,
    ) : BaseViewModel() {
        private val _materialsEntity = MutableStateFlow<MaterialsEntity>(MaterialsEntity.EMPTY)
        val materialsEntity: StateFlow<MaterialsEntity> get() = _materialsEntity

        private val _productEntity = MutableStateFlow<List<ProductEntity>>(emptyList())
        val productEntity: StateFlow<List<ProductEntity>> get() = _productEntity

        private val _materialName = MutableLiveData<String>()
        val materialName: LiveData<String> get() = _materialName

//        private fun getStockIn(smartOrderSelect: Boolean) {
//            requestApi(
//                request = {
//                    mainUseCase.getMenuCategory(
//                        token = preferenceUseCase.getToken(),
//                        storeId = "${preferenceUseCase.getStoreId()}",
//                    )
//                },
//                successAction = { response ->
//                    log.e("smartOrderSelect: $smartOrderSelect      $response")
//                },
//                errorAction = { code, message ->
//                    log.e("code: $code, message: $message")
//                    Utils.showToast(message)
//                },
//            )
//        }

        fun setMaterial(materialsEntity: MaterialsEntity) {
            _materialsEntity.value = materialsEntity
            _materialName.value = materialsEntity.materialName
        }
    }
