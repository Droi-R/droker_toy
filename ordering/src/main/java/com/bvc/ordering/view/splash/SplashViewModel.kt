package com.bvc.ordering.view.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bvc.domain.model.GithubEntity
import com.bvc.domain.type.ScreenState
import com.bvc.domain.usecase.GetUserRepoUseCase
import com.bvc.domain.usecase.PreferenceUseCase
import com.bvc.ordering.base.BaseViewModel
import com.bvc.ordering.base.SingleLiveEvent
import com.bvc.ordering.ksnet.Telegram
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
    @Inject
    constructor(
        private val getUserRepoUseCase: GetUserRepoUseCase,
        private val preferenceUseCase: PreferenceUseCase,
    ) : BaseViewModel() {
        val eventUserRepo: LiveData<List<GithubEntity>> get() = _eventUserRepo
        private val _eventUserRepo = SingleLiveEvent<List<GithubEntity>>()

        private val _requestTelegram = SingleLiveEvent<ByteArray>()
        val requestTelegram: LiveData<ByteArray> get() = _requestTelegram

        private val _affiliate =
            SingleLiveEvent<String>().apply {
                value = ""
            }
        val affiliate: LiveData<String> get() = _affiliate

        private val _affiliateName =
            SingleLiveEvent<String>().apply {
                value = "로그인"
            }
        val affiliateName: LiveData<String> get() = _affiliateName

        private val _startVisible =
            SingleLiveEvent<Boolean>().apply {
                value = false
            }
        val startVisible: LiveData<Boolean> get() = _startVisible

        init {
            viewModelScope.launch {
                val token = preferenceUseCase.getToken()
                if (token.isNotEmpty()) {
                    getAffiliate()
                }
            }
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
                )
        }

        private fun getAffiliate() {
            viewModelScope.launch {
                val response = getUserRepoUseCase.execute(this@SplashViewModel, preferenceUseCase.getToken())
                if (response == null) {
                    mutableScreenState.postValue(ScreenState.ERROR)
                } else {
                    mutableScreenState.postValue(ScreenState.RENDER)
                    _eventUserRepo.postValue(response)
                    _affiliateName.value = response.first().name
                    _affiliate.value = "가맹점"
                    _startVisible.value = true
                }
            }
        }
    }
