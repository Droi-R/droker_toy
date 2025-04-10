package com.bvc.ordering.base

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bvc.domain.type.ApiStatus
import com.bvc.domain.type.ErrorType
import com.bvc.domain.type.ScreenState
import com.bvc.domain.utils.Constant
import com.bvc.domain.utils.RemoteErrorEmitter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel :
    ViewModel(),
    RemoteErrorEmitter {
    val mutableProgress = MutableLiveData<Int>(View.GONE)
    val mutableScreenState = SingleLiveEvent<ScreenState>()
    val mutableErrorMessage = SingleLiveEvent<String>()
    val mutableSuccessMessage = MutableLiveData<String>()
    val mutableErrorType = SingleLiveEvent<ErrorType>()

    override fun onError(errorType: ErrorType) {
        mutableErrorType.postValue(errorType)
    }

    override fun onError(msg: String) {
        mutableErrorMessage.postValue(msg)
    }

    fun <T> requestApi(
        request: suspend () -> T?,
        successAction: (T) -> Unit,
        errorAction: (String) -> Unit,
    ) {
        viewModelScope.launch {
            mutableScreenState.postValue(ScreenState.LOADING)
            val response = withContext(Dispatchers.IO) { request() }
            mutableScreenState.postValue(ScreenState.RENDER)

            if (response == null) {
                mutableScreenState.postValue(ScreenState.ERROR)
                return@launch
            }

            val meta =
                response.javaClass
                    .getDeclaredField("meta")
                    .apply { isAccessible = true }
                    .get(response)
            val code =
                meta.javaClass
                    .getDeclaredField("code")
                    .apply { isAccessible = true }
                    .get(meta) as Int
            val message =
                meta.javaClass
                    .getDeclaredField("message")
                    .apply { isAccessible = true }
                    .get(meta) as String

            when (Constant.getStatus(code)) {
                ApiStatus.SUCCESS -> successAction(response)
                else -> errorAction(message)
            }
        }
    }
}
