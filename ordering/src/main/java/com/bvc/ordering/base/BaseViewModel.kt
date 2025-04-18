package com.bvc.ordering.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bvc.domain.type.ApiStatus
import com.bvc.domain.type.ScreenState
import com.bvc.domain.utils.Constant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel : ViewModel() {
    val mutableScreenState = SingleLiveEvent<ScreenState>()

    fun <T> requestApi(
        request: suspend () -> T?,
        successAction: (T) -> Unit,
        errorAction: (Int, String) -> Unit,
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
                else -> errorAction(code, message)
            }
        }
    }
}
