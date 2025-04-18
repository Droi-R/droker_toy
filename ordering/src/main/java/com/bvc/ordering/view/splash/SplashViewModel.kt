package com.bvc.ordering.view.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bvc.domain.model.GithubEntity
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

        private val _action = SingleLiveEvent<Int>()
        val action: LiveData<Int> get() = _action
        private var loginLevel = 0

        init {

            viewModelScope.launch {
                if (preferenceUseCase.getToken().isNotEmpty()) {
                    loginLevel = 1
                    if (preferenceUseCase.getStoreId() != -1) {
                        _affiliateName.value = preferenceUseCase.getStoreName() ?: "로그인"
                        _affiliate.value = preferenceUseCase.getStoreType() ?: "비가맹점"
                        _startVisible.value = true
                        loginLevel = 2
                    }
                }
            }
        }

        fun onClickLogin() {
            _action.value = loginLevel
        }
    }
