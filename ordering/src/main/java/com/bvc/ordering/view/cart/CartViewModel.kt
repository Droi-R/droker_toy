package com.bvc.ordering.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bvc.domain.log
import com.bvc.domain.model.ApiData
import com.bvc.domain.model.OrderEntity
import com.bvc.domain.model.PaymentEntity
import com.bvc.domain.model.ProductEntity
import com.bvc.domain.model.calculateVatSummary
import com.bvc.domain.repository.ProductStoreRepository
import com.bvc.domain.type.ApiStatus
import com.bvc.domain.type.OrderFrom
import com.bvc.domain.type.OrderStatus
import com.bvc.domain.type.PaymentChannel
import com.bvc.domain.type.PaymentMethod
import com.bvc.domain.type.PaymentStatus
import com.bvc.domain.type.PaymentType
import com.bvc.domain.usecase.MainUseCase
import com.bvc.domain.usecase.PreferenceUseCase
import com.bvc.domain.utils.Constant
import com.bvc.ordering.base.BaseViewModel
import com.bvc.ordering.base.SingleLiveEvent
import com.bvc.ordering.ksnet.Telegram
import com.bvc.ordering.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel
    @Inject
    constructor(
        private val preferenceUseCase: PreferenceUseCase,
        private val getMainUseCase: MainUseCase,
        private val productStoreRepository: ProductStoreRepository,
    ) : BaseViewModel() {
        private val _affiliteType = SingleLiveEvent<String>()
        val affiliteType: LiveData<String> get() = _affiliteType

        private val _affiliteName = SingleLiveEvent<String>()
        val affiliteName: LiveData<String> get() = _affiliteName

        private val _supplyAmount = MutableStateFlow(0)
        val supplyAmount: StateFlow<Int> get() = _supplyAmount

        private val _vatAmount = MutableStateFlow(0)
        val vatAmount: StateFlow<Int> get() = _vatAmount

        private val _totalAmount = MutableStateFlow(0)
        val totalAmount: StateFlow<Int> get() = _totalAmount

        private val _taxFreeAmount = MutableStateFlow(0)
        val taxFreeAmount: StateFlow<Int> get() = _taxFreeAmount

        private val _cartData = MutableStateFlow<List<ProductEntity>>(emptyList())
        val cartData: StateFlow<List<ProductEntity>> get() = _cartData

        private val _requestTelegram = SingleLiveEvent<Pair<ByteArray, PaymentEntity>>()
        val requestTelegram: LiveData<Pair<ByteArray, PaymentEntity>> get() = _requestTelegram

        init {

            viewModelScope.launch {
                _affiliteName.value = preferenceUseCase.getStoreName() ?: ""
                _affiliteType.value = preferenceUseCase.getStoreType() ?: ""
            }
            getCartStore()
        }

        fun getCartStore() {
            viewModelScope.launch {
                _cartData.value = productStoreRepository.getItems()
                calculateAmounts(cartData.value)
            }
        }

        private fun calculateAmounts(items: List<ProductEntity>) {
            val result = items.calculateVatSummary()

            _supplyAmount.value = result.supplyAmount
            _vatAmount.value = result.vat
            _totalAmount.value = result.totalAmount
            log.e("taxFree: ${result.taxFree}")
        }

        fun deleteItem(item: ProductEntity) {
            viewModelScope.launch {
                productStoreRepository.removeItem(item)
                getCartStore()
            }
        }

        fun minusItem(item: ProductEntity) {
            viewModelScope.launch {
                productStoreRepository.minusItem(item)
                getCartStore()
            }
        }

        fun plusItem(item: ProductEntity) {
            viewModelScope.launch {
                productStoreRepository.addItem(item)
                getCartStore()
            }
        }

        fun postOrder() {
            requestApi(
                request = {
                    getMainUseCase
                        .postOrder(
                            token = preferenceUseCase.getToken(),
                            userId = preferenceUseCase.getUserId(),
                            storeId = "${preferenceUseCase.getStoreId()}",
                            productItems = cartData.value,
                            orderStatus = OrderStatus.PENDING,
                            orderFrom = OrderFrom.POS,
                            tablesId = 0,
                            itemMemo = "",
                            totalPrice = totalAmount.value,
                            supplyPrice = supplyAmount.value,
                            vatPrice = vatAmount.value,
                            discountPrice = 0,
                        )
                },
                successAction = { response ->
                    log.e("response: $response")
                    if (response.meta.code == 200) {
                        Utils.showToast("주문이 완료되었습니다.")
                    } else {
                        Utils.showToast("주문에 실패하였습니다.")
                    }
                    postPayment(response)
                },
                errorAction = { code, message ->
                    log.e("code: $code, message: $message")
                    Utils.showToast(message)
                },
            )
        }

        fun postPayment(response: ApiData<OrderEntity>) {
            requestApi(
                request = {
                    getMainUseCase
                        .postPayment(
                            token = preferenceUseCase.getToken(),
                            userId = preferenceUseCase.getUserId(),
                            storeId = "${preferenceUseCase.getStoreId()}",
                            orderProductIds = listOf(response.data.orderID),
                            totalPrice = "${totalAmount.value}",
                            paymentMethod = PaymentMethod.CARD,
                            paymentChannel = PaymentChannel.KAKAO,
                            paymentStatus = PaymentStatus.READY,
                            paymentType = PaymentType.PREPAID,
                        )
                },
                successAction = { response ->
                    if (Constant.getStatus(response.meta.code) == ApiStatus.SUCCESS) {
                        response.data.vat = "${vatAmount.value}"
                        response.data.supAmt = "${supplyAmount.value}"
                        response.data.paymentAmout = "${totalAmount.value}"
                        response.data.taxFreeAmt = "${taxFreeAmount.value}"
                        _requestTelegram.value =
                            Pair(
                                Telegram.makeTelegramIC(
                                    apprCode = "1",
                                    mDeviceNo = "DPT0TEST03",
                                    quota = "00",
                                    totAmt = "${totalAmount.value}",
                                    orgApprNo = "",
                                    orgDate = "",
                                    taxFree = "${taxFreeAmount.value}",
                                ),
                                response.data,
                            )
                    }
//            }
                },
                errorAction = { code, message ->
                    log.e("code: $code, message: $message")
                    Utils.showToast(message)
                },
            )
        }
    }
