package com.bvc.ordering.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bvc.domain.model.MaterialsEntity
import com.bvc.domain.model.PaymentEntity
import com.bvc.domain.model.ProductEntity
import com.bvc.domain.model.TableEntity
import com.bvc.domain.usecase.MainUseCase
import com.bvc.domain.usecase.PreferenceUseCase
import com.bvc.ordering.base.BaseViewModel
import com.bvc.ordering.base.SingleLiveEvent
import com.bvc.ordering.ui.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val preferenceUseCase: PreferenceUseCase,
        private val mainUseCase: MainUseCase,
    ) : BaseViewModel() {
        private val _requestTelegram = SingleLiveEvent<Pair<ByteArray, PaymentEntity>>()
        val requestTelegram: LiveData<Pair<ByteArray, PaymentEntity>> get() = _requestTelegram

        private val _affiliteType = SingleLiveEvent<String>()
        val affiliteType: LiveData<String> get() = _affiliteType

        private val _affiliteName = SingleLiveEvent<String>()
        val affiliteName: LiveData<String> get() = _affiliteName

        private val _alarmVisibility =
            SingleLiveEvent<Boolean>().apply {
                value = false
            }
        val alarmVisibility: LiveData<Boolean> get() = _alarmVisibility

        private val _alarmCount = SingleLiveEvent<String>()
        val alarmCount: LiveData<String> get() = _alarmCount

        private val _businessStatus =
            SingleLiveEvent<String>()
                .apply {
                    value = "오픈 대기"
                }
        val businessStatus: LiveData<String> get() = _businessStatus

        private val _isBusiness =
            SingleLiveEvent<Boolean>()
                .apply {
                    value = false
                }
        val isBusiness: LiveData<Boolean> get() = _isBusiness

        private val _tableEventFlow = MutableSharedFlow<Event<TableEntity>>(replay = 1)
        val tableEventFlow = _tableEventFlow.asSharedFlow()

        private val _matarialEventFlow = MutableSharedFlow<Event<MaterialsEntity>>(replay = 1)
        val matarialEventFlow = _matarialEventFlow.asSharedFlow()

        private val _productEventFlow = MutableSharedFlow<Event<ProductEntity>>(replay = 1)
        val productEventFlow = _productEventFlow.asSharedFlow()

        init {
            getStore()
        }

        private fun getStore() {
            requestApi(
                request = {
                    mainUseCase.getStore(
                        preferenceUseCase.getToken(),
                        "${preferenceUseCase.getStoreId()}",
                    )
                },
                successAction = {
                    _affiliteName.value = it.data.name
                    _affiliteType.value = if (it.data.cats.isNotEmpty()) "가맹" else "비가맹"
                    _isBusiness.value = it.data.isActive
                    if (isBusiness.value == true) {
                        _businessStatus.value = "영업중"
                    } else {
                        _businessStatus.value = "오픈 대기"
                    }
                },
                errorAction = { code, message ->
                    when (code) {
                    }
                },
            )
        }

        fun requestTelegram(pair: Pair<ByteArray, PaymentEntity>) {
            _requestTelegram.value = pair
        }

        fun sendTableEvent(table: TableEntity) {
            val copiedTable =
                table.copy(
                    orders =
                        table.orders.map { order ->
                            order.copy(
                                orderItems = order.orderItems.map { it.copy() },
                            )
                        },
                )
            viewModelScope.launch {
                _tableEventFlow.emit(Event(copiedTable))
            }
        }

        fun sendMaterialEvent(material: MaterialsEntity) {
            val copiedMaterial = material.copy()
            viewModelScope.launch {
                _matarialEventFlow.emit(Event(copiedMaterial))
            }
        }

        fun sendProductEvent(product: ProductEntity) {
            val copiedProduct = product.copy()
            viewModelScope.launch {
                _productEventFlow.emit(Event(copiedProduct))
            }
        }

        fun requestCapture(
            apprNo: String,
            apprDate: String,
        ) {
            requestApi(
                request = {
                    mainUseCase.requestCapture(
                        preferenceUseCase.getToken(),
                    )
                },
                successAction = {
                    _alarmVisibility.value = true
                    _alarmCount.value = it.data.toString()
                },
                errorAction = { code, message ->
                    when (code) {
                    }
                },
            )
        }
    }
