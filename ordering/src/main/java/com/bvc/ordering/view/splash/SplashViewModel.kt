package com.bvc.ordering.view.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bvc.domain.log
import com.bvc.domain.model.GithubEntity
import com.bvc.domain.type.ScreenState
import com.bvc.domain.usecase.GetUserRepoUseCase
import com.bvc.domain.usecase.PreferenceUseCase
import com.bvc.domain.usecase.SplashUseCase
import com.bvc.ordering.base.BaseViewModel
import com.bvc.ordering.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
    @Inject
    constructor(
        private val preferenceUseCase: PreferenceUseCase,
        private val getUserRepoUseCase: GetUserRepoUseCase,
        private val splashUseCase: SplashUseCase,
    ) : BaseViewModel() {
        val eventUserRepo: LiveData<List<GithubEntity>> get() = _eventUserRepo
        private val _eventUserRepo = SingleLiveEvent<List<GithubEntity>>()

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

        private val _action = SingleLiveEvent<Boolean>()
        val action: LiveData<Boolean> get() = _action

        init {
            viewModelScope.launch {
                val token = preferenceUseCase.getToken()
                log.e("token : $token")
                if (token.isNotEmpty()) {
                    getAffiliate()
                }
            }
        }

        private fun getAffiliate() {
            viewModelScope.launch {
                val response =
                    getUserRepoUseCase.getGithub(this@SplashViewModel, preferenceUseCase.getToken())
                log.e("response : $response")
                if (response == null) {
                    mutableScreenState.postValue(ScreenState.ERROR)
                } else {
                    mutableScreenState.postValue(ScreenState.RENDER)
//                    _eventUserRepo.postValue(response)
                    _affiliateName.value = response.first().name
                    _affiliate.value = "가맹점"
                    _startVisible.value = true
                }
            }
        }

        fun onClickLogin() {
            _action.value = _startVisible.value
        }
    }
