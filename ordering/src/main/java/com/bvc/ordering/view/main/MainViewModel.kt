package com.bvc.ordering.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bvc.domain.log
import com.bvc.domain.model.MaterialsEntity
import com.bvc.domain.model.PaymentEntity
import com.bvc.domain.model.ProductEntity
import com.bvc.domain.model.TableEntity
import com.bvc.domain.repository.ProductStoreRepository
import com.bvc.domain.type.ApiStatus
import com.bvc.domain.usecase.MainUseCase
import com.bvc.domain.usecase.PreferenceUseCase
import com.bvc.domain.utils.Constant
import com.bvc.ordering.base.BaseViewModel
import com.bvc.ordering.base.SingleLiveEvent
import com.bvc.ordering.ksnet.Telegram
import com.bvc.ordering.ksnet.TransactionData
import com.bvc.ordering.ui.event.Event
import com.bvc.ordering.util.Utils
import com.bvc.ordering.view.order.OrderFragment
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
        private val productStoreRepository: ProductStoreRepository,
    ) : BaseViewModel() {
        private val _requestCaptureTelegram = SingleLiveEvent<Pair<ByteArray, PaymentEntity>>()
        val requestCaptureTelegram: LiveData<Pair<ByteArray, PaymentEntity>> get() = _requestCaptureTelegram

        private val _requestRefundTelegram = SingleLiveEvent<Pair<ByteArray, PaymentEntity>>()
        val requestRefundTelegram: LiveData<Pair<ByteArray, PaymentEntity>> get() = _requestRefundTelegram

        private val _affiliteType = SingleLiveEvent<String>()
        val affiliteType: LiveData<String> get() = _affiliteType

        private val _affiliteName = SingleLiveEvent<String>()
        val affiliteName: LiveData<String> get() = _affiliteName

        private val _captureAfterAction = SingleLiveEvent<String>()
        val captureAfterAction: LiveData<String> get() = _captureAfterAction

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
                    log.e("code: $code, message: $message")
                    Utils.showToast(message)
                },
            )
        }

        fun requestCapture(recvByte: ByteArray) {
            requestApi(
                request = {
                    val trData = TransactionData()
                    trData.SetData(recvByte)
                    mainUseCase.requestCapture(
                        token = preferenceUseCase.getToken(),
                        paymentId = requestCaptureTelegram.value?.second?.paymentId ?: throw IllegalStateException("Payment ID is null"),
                        amount =
                            requestCaptureTelegram.value
                                ?.second
                                ?.paymentAmout
                                ?.toDouble() ?: throw IllegalStateException(
                                "Payment Amount is null",
                            ),
                        deviceId = String(trData.deviceNumber),
                        approvedId = String(trData.approvalNumber),
                        approvedDate = String(trData.transferDate),
                    )
                },
                successAction = {
                    if (Constant.getStatus(it.meta.code) == ApiStatus.SUCCESS) {
//                        productStoreRepository.clearCart()
                        _captureAfterAction.value = OrderFragment::class.java.name

                        // 환불 테스트
                        val trData = TransactionData()
                        trData.SetData(recvByte)
                        requestRefundTelegram(
                            Pair(
                                Telegram.makeTelegramIC(
                                    apprCode = "1",
                                    mDeviceNo = String(trData.deviceNumber),
                                    quota = "00",
                                    totAmt = "${requestCaptureTelegram.value?.second?.paymentAmout}",
                                    orgApprNo = String(trData.approvalNumber),
                                    orgDate = String(trData.transferDate),
                                    taxFree = "${requestCaptureTelegram.value?.second?.supAmt}",
                                ),
                                PaymentEntity(
                                    paymentId =
                                        requestCaptureTelegram.value?.second?.paymentId ?: throw IllegalStateException(
                                            "Payment ID is null",
                                        ),
                                    paymentAmout = "${requestCaptureTelegram.value?.second?.paymentAmout ?: 0}",
                                    vat = "${requestCaptureTelegram.value?.second?.vat}",
                                    supAmt = "${requestCaptureTelegram.value?.second?.supAmt}",
                                ),
                            ),
                        )
                    } else {
                        Utils.showToast(it.meta.message)
                    }
                },
                errorAction = { code, message ->
                    log.e("code: $code, message: $message")
                    Utils.showToast(message)
                },
            )
        }

        fun requestRefund(recvByte: ByteArray) {
            requestApi(
                request = {
                    val trData = TransactionData()
                    trData.SetData(recvByte)
                    mainUseCase.reqeustRefund(
                        token = preferenceUseCase.getToken(),
                        paymentId = requestCaptureTelegram.value?.second?.paymentId ?: throw IllegalStateException("Payment ID is null"),
                        amount =
                            requestCaptureTelegram.value
                                ?.second
                                ?.paymentAmout
                                ?.toDouble() ?: throw IllegalStateException(
                                "Payment Amount is null",
                            ),
                        deviceId = String(trData.deviceNumber),
                        approvedId = String(trData.approvalNumber),
                        approvedDate = String(trData.transferDate),
                    )
                },
                successAction = {
                    if (Constant.getStatus(it.meta.code) == ApiStatus.SUCCESS) {
                        productStoreRepository.clearCart()
                        _captureAfterAction.value = OrderFragment::class.java.name
                    } else {
                        Utils.showToast(it.meta.message)
                    }
                },
                errorAction = { code, message ->
                    log.e("code: $code, message: $message")
                    Utils.showToast(message)
                },
            )
        }

        fun requestCaptureTelegram(pair: Pair<ByteArray, PaymentEntity>) {
            _requestCaptureTelegram.value = pair
        }

        fun requestRefundTelegram(pair: Pair<ByteArray, PaymentEntity>) {
            _requestRefundTelegram.value = pair
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
    }
