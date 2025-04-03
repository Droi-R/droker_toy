package com.bvc.ordering.view.main

import androidx.lifecycle.LiveData
import com.bvc.domain.usecase.MainUseCase
import com.bvc.domain.usecase.PreferenceUseCase
import com.bvc.ordering.base.BaseViewModel
import com.bvc.ordering.base.SingleLiveEvent
import com.bvc.ordering.ksnet.Telegram
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val preferenceUseCase: PreferenceUseCase,
        private val mainUseCase: MainUseCase,
    ) : BaseViewModel() {
        companion object {
        }

        private val _requestTelegram = SingleLiveEvent<ByteArray>()
        val requestTelegram: LiveData<ByteArray> get() = _requestTelegram

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

        init {
        }

        fun onSplashClick() {
            _requestTelegram.value =
                Telegram.makeTelegramIC(
                    apprCode = "1",
                    mDeviceNo = "DPT0TEST03",
                    quota = "00",
                    totAmt = "1004",
                    orgApprNo = "123456789012",
                    orgDate = "201020",
                    taxFree = "000000000000",
                )
        }
    }
