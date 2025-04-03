package com.bvc.ordering.view.splash.select

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bvc.domain.model.AffiliateEntity
import com.bvc.domain.type.ScreenState
import com.bvc.domain.usecase.PreferenceUseCase
import com.bvc.domain.usecase.SplashUseCase
import com.bvc.ordering.base.BaseViewModel
import com.bvc.ordering.base.SingleLiveEvent
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
        private val _affiliate =
            SingleLiveEvent<List<AffiliateEntity>>()
        val affiliate: LiveData<List<AffiliateEntity>> get() = _affiliate

        private val _action = SingleLiveEvent<Boolean>()
        val action: LiveData<Boolean> get() = _action

        init {
            getAffiliate()
        }

        private fun getAffiliate() {
            viewModelScope.launch {
                val response =
//                    getUserRepoUseCase.getGithub(this@SelectViewModel, preferenceUseCase.getToken())
                    splashUseCase.getAffiliate(this@SelectViewModel, preferenceUseCase.getToken())
//                log.e("response : $response")

                _affiliate.value = (
                    response?.data?.map {
                        AffiliateEntity(
                            tid = it.tid,
                            name = it.name,
                            type = it.type,
                        )
                    } ?: listOf(
                        AffiliateEntity(
                            tid = "",
                            name = "쿠바노스",
                            type = "가맹점",
                        ),
                    )
                ) +
                    listOf(
                        AffiliateEntity(
                            tid = "",
                            name = "매장 추가하기",
                            type = "",
                        ),
                    )

                if (response == null) {
                    mutableScreenState.postValue(ScreenState.ERROR)
                } else {
                    mutableScreenState.postValue(ScreenState.RENDER)
                }
            }
        }
    }
