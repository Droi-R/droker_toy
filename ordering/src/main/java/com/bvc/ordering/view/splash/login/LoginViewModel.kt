package com.bvc.ordering.view.splash.login

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bvc.domain.log
import com.bvc.domain.type.ScreenState
import com.bvc.domain.usecase.GetUserRepoUseCase
import com.bvc.domain.usecase.PreferenceUseCase
import com.bvc.ordering.base.BaseViewModel
import com.bvc.ordering.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val getUserRepoUseCase: GetUserRepoUseCase,
        private val preferenceUseCase: PreferenceUseCase,
    ) : BaseViewModel() {
        private val _phoneNum =
            SingleLiveEvent<String>().apply {
                value = ""
            }
        val phoneNum: LiveData<String> get() = _phoneNum

        private val _verification =
            SingleLiveEvent<String>().apply {
                value = ""
            }
        val verification: LiveData<String> get() = _verification

        private val _action = SingleLiveEvent<Boolean>()
        val action: LiveData<Boolean> get() = _action

        fun loginPhoneAfterTextChanged(editable: Editable?) {
            if (editable == null) {
                return
            }

            try {
                _phoneNum.value = editable.toString().replace("-", "")
            } catch (e: Exception) {
                log.e(e)
            }
        }

        fun loginVerificationAfterTextChanged(editable: Editable?) {
            if (editable == null) {
                return
            }

            try {
                _verification.value = editable.toString().replace("-", "")
            } catch (e: Exception) {
                log.e(e)
            }
        }

        fun onClickVerification() {
            viewModelScope.launch {
//                val response = getUserRepoUseCase.getGithub(this@LoginViewModel, preferenceUseCase.getToken())
                val response = getUserRepoUseCase.getGithub(this@LoginViewModel, "sam")
//                log.e("response : $response")
                if (response == null) {
                    mutableScreenState.postValue(ScreenState.ERROR)
                } else {
                    mutableScreenState.postValue(ScreenState.RENDER)
                    _action.value = true
                }
            }
        }

        fun onClickSend() {
        }

        fun onClickResend() {
        }
    }
