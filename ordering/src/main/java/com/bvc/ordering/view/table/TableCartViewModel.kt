package com.bvc.ordering.view.table

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bvc.domain.log
import com.bvc.domain.model.ProductEntity
import com.bvc.domain.model.TableEntity
import com.bvc.domain.model.calculateVatSummary
import com.bvc.domain.repository.TableStoreRepository
import com.bvc.domain.type.OrderFrom
import com.bvc.domain.type.OrderStatus
import com.bvc.domain.type.PaymentStatus
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
class TableCartViewModel
    @Inject
    constructor(
        private val preferenceUseCase: PreferenceUseCase,
        private val getMainUseCase: MainUseCase,
        private val cartStoreRepository: TableStoreRepository,
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

        private val _requestTelegram = SingleLiveEvent<ByteArray>()
        val requestTelegram: LiveData<ByteArray> get() = _requestTelegram

        private val _tableInfo = SingleLiveEvent<TableEntity>()
        val tableInfo: LiveData<TableEntity> get() = _tableInfo

        private val _tableName = SingleLiveEvent<String>()
        val tableName: LiveData<String> get() = _tableName

        init {

            viewModelScope.launch {
                _affiliteName.value = preferenceUseCase.getStoreName() ?: ""
                _affiliteType.value = preferenceUseCase.getStoreType() ?: ""
            }
//            getCartStore()
        }

        fun getCartStore() {
            viewModelScope.launch {
                _cartData.value = cartStoreRepository.getItems()
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
                cartStoreRepository.removeItem(item)
                getCartStore()
            }
        }

        fun minusItem(item: ProductEntity) {
            viewModelScope.launch {
                cartStoreRepository.minusItem(item)
                getCartStore()
            }
        }

        fun plusItem(item: ProductEntity) {
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
                            remoteErrorEmitter = this@TableCartViewModel,
                            token = preferenceUseCase.getToken(),
                            id = "",
                            productItems = cartData.value,
                            orderStatus = OrderStatus.PENDING,
                            paymentStatus = PaymentStatus.READY,
                            orderFrom = OrderFrom.POS,
                            tableNumber = "",
                            tableExternalKey = "",
                        )
                log.e("response: ${cartData.value}")
                // TODO 여기서 페이먼트 생성
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

        fun setTableInfo(table: TableEntity) {
            _tableInfo.value = table
            _tableName.value = table.tableName
            tableInfo.value?.orders?.map {
                it.orderItems.map { item ->
                    cartStoreRepository.addItem(item)
                }
            }
            getCartStore()
        }

        fun clearCart() {
            cartStoreRepository.clearCart()
        }
    }
