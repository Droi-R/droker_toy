package com.bvc.ordering.view.splash.login

import android.content.Context
import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bvc.domain.log
import com.bvc.domain.usecase.PreferenceUseCase
import com.bvc.domain.usecase.SplashUseCase
import com.bvc.ordering.base.BaseViewModel
import com.bvc.ordering.base.SingleLiveEvent
import com.bvc.ordering.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val splashUseCase: SplashUseCase,
        private val preferenceUseCase: PreferenceUseCase,
        @ApplicationContext private val context: Context,
    ) : BaseViewModel() {
        private val _phoneNum = MutableLiveData<String>("")
        val phoneNum: LiveData<String> = _phoneNum

        private val _verification = MutableLiveData<String>("")
        val verification: LiveData<String> = _verification

        private val _enableVerify = MutableLiveData<Boolean>(false)
        val enableVerify: LiveData<Boolean> = _enableVerify

        private val _action = SingleLiveEvent<Boolean>()
        val action: LiveData<Boolean> = _action

        fun loginPhoneAfterTextChanged(editable: Editable?) {
            _phoneNum.value = editable?.toString()?.replace("-", "") ?: ""
        }

        fun loginVerificationAfterTextChanged(editable: Editable?) {
            _verification.value = editable?.toString()?.replace("-", "") ?: ""
        }

        fun onClickSend() {
            requestApi(
                request = { splashUseCase.sendSms(this@LoginViewModel, phoneNum.value.orEmpty()) },
                successAction = {
//                    _enableVerify.value = true
                    _enableVerify.postValue(true)
                    Utils.showToast("인증번호가 발송되었습니다.")
                },
                errorAction = { code, message ->
                    _enableVerify.value = false
                    Utils.showToast(message)
                },
            )
        }

        fun onClickVerification() {
            requestApi(
                request = {
                    splashUseCase.verifySms(
                        this@LoginViewModel,
                        phoneNum.value.orEmpty(),
                        verification.value.orEmpty(),
                    )
                },
                successAction = {
                    Utils.showToast("인증번호가 확인되었습니다.")
                    // 값에따라 로그인 or 회원가입
                    signUp()
                },
                errorAction = { code, message ->
                    if (code == 207) {
                        login()
                    } else {
                        Utils.showToast(message)
                    }
                },
            )
        }

        private fun signUp() {
            requestApi(
                request = {
                    splashUseCase.signUp(
                        this@LoginViewModel,
                        phoneNum.value.orEmpty(),
                        verification.value.orEmpty(),
                    )
                },
                successAction = { response ->
                    viewModelScope.launch(Dispatchers.IO) {
                        preferenceUseCase.setToken("Bearer ${response.data.accessToken}")
                        preferenceUseCase.setUserId(response.data.user.userId)
                        preferenceUseCase.setRefreshToken(response.data.refreshToken)
                    }
                    _action.value = true
                },
                errorAction = { code, message ->
                    Utils.showToast(message)
                },
            )
        }

        private fun login() {
            requestApi(
                request = {
                    splashUseCase.getLogin(
                        this@LoginViewModel,
                        phoneNum.value.orEmpty(),
                        verification.value.orEmpty(),
                    )
                },
                successAction = { response ->
                    log.e("response: $response")
                    viewModelScope.launch(Dispatchers.IO) {
                        preferenceUseCase.setToken("Bearer ${response.data.accessToken}")
                        preferenceUseCase.setUserId(response.data.user.userId)
                        preferenceUseCase.setRefreshToken(response.data.refreshToken)
                    }
                    _action.value = true
                },
                errorAction = { code, message ->
                    Utils.showToast(message)
                },
            )
        }
    }
