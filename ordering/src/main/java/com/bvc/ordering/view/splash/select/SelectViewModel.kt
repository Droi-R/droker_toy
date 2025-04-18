package com.bvc.ordering.view.splash.select

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bvc.domain.log
import com.bvc.domain.model.Store
import com.bvc.domain.usecase.PreferenceUseCase
import com.bvc.domain.usecase.SplashUseCase
import com.bvc.ordering.base.BaseViewModel
import com.bvc.ordering.base.SingleLiveEvent
import com.bvc.ordering.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectViewModel
    @Inject
    constructor(
        private val splashUseCase: SplashUseCase,
        private val preferenceUseCase: PreferenceUseCase,
    ) : BaseViewModel() {
        private val _affiliate = SingleLiveEvent<List<Store>>()
        val affiliate: LiveData<List<Store>> get() = _affiliate

        private val _action = SingleLiveEvent<Int>()
        val action: LiveData<Int> get() = _action

        init {
            getStore()
            viewModelScope.launch {
                log.e("preferenceUseCase.getUserId(): ${preferenceUseCase.getUserId()}")
            }
        }

        private fun getStore() {
            requestApi(
                request = {
                    splashUseCase.getStore(
                        this@SelectViewModel,
                        preferenceUseCase.getToken(),
                        preferenceUseCase.getUserId(),
                    )
                },
                successAction = {
                    _affiliate.value =
                        buildList {
                            addAll(it.data.orEmpty())
                            add(Store(name = "매장 추가하기", isActive = false))
                        }
                },
                errorAction = { code, message ->
                    log.e("code: $code, message: $message")
                    Utils.showToast(message)
                    viewModelScope.launch {
                        if (code == 401) {
                            preferenceUseCase.setToken("")
                            preferenceUseCase.setStoreId(-1)
                            _action.value = 2
                        }
                    }
                },
            )
        }

        fun selectStore(item: Store) {
            log.e("item: $item")
            viewModelScope.launch {
                preferenceUseCase.setStoreId(item.storeId)
                preferenceUseCase.setStoreName(item.name)
                _action.value = 1
            }
        }
    }
