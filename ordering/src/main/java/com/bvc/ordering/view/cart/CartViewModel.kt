package com.bvc.ordering.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bvc.domain.log
import com.bvc.domain.model.CartEntity
import com.bvc.domain.model.calculateVatSummary
import com.bvc.domain.repository.CartStoreRepository
import com.bvc.domain.type.OrderFrom
import com.bvc.domain.type.OrderStatus
import com.bvc.domain.usecase.MainUseCase
import com.bvc.domain.usecase.PreferenceUseCase
import com.bvc.ordering.base.BaseViewModel
import com.bvc.ordering.base.SingleLiveEvent
import com.bvc.ordering.ksnet.Telegram
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
        private val cartStoreRepository: CartStoreRepository,
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

        private val _cartData = MutableStateFlow<List<CartEntity>>(emptyList())
        val cartData: StateFlow<List<CartEntity>> get() = _cartData

        private val _requestTelegram = SingleLiveEvent<ByteArray>()
        val requestTelegram: LiveData<ByteArray> get() = _requestTelegram

        init {

            viewModelScope.launch {
                _affiliteName.value = preferenceUseCase.getAffiliteName() ?: ""
                _affiliteType.value = preferenceUseCase.getAffiliteType() ?: ""
            }
            getCartStore()
        }

        fun getCartStore() {
            viewModelScope.launch {
                _cartData.value = cartStoreRepository.getItems()
                calculateAmounts(cartData.value)
            }
        }

        private fun calculateAmounts(items: List<CartEntity>) {
            val result = items.calculateVatSummary()

            _supplyAmount.value = result.supplyAmount
            _vatAmount.value = result.vat
            _totalAmount.value = result.totalAmount
            log.e("taxFree: ${result.taxFree}")
        }

        fun deleteItem(item: CartEntity) {
            viewModelScope.launch {
                cartStoreRepository.removeItem(item)
                getCartStore()
            }
        }

        fun minusItem(item: CartEntity) {
            viewModelScope.launch {
                cartStoreRepository.minusItem(item)
                getCartStore()
            }
        }

        fun plusItem(item: CartEntity) {
            viewModelScope.launch {
                cartStoreRepository.addItem(item)
                getCartStore()
            }
        }

        fun postOrder() {
            viewModelScope.launch {
                val response =
                    getMainUseCase
                        .postOrder(
                            remoteErrorEmitter = this@CartViewModel,
                            token = preferenceUseCase.getToken(),
                            id = "",
                            productItems = cartData.value,
                            status = OrderStatus.READY,
                            orderFrom = OrderFrom.POS,
                            tableNumber = "",
                            tableExternalKey = "",
                        )
                log.e("response: ${cartData.value}")
                _requestTelegram.value =
                    Telegram.makeTelegramIC(
                        apprCode = "1",
                        mDeviceNo = "DPT0TEST03",
                        quota = "00",
                        totAmt = "${totalAmount.value}",
                        orgApprNo = "",
                        orgDate = "",
                        taxFree = "${taxFreeAmount.value}",
                    )
            }
        }
    }
