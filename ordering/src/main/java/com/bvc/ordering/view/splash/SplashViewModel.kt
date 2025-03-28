package com.bvc.ordering.view.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bvc.domain.model.YoEntity
import com.bvc.domain.usecase.GetUserUseCase
import com.bvc.ordering.base.BaseViewModel
import com.bvc.ordering.base.SingleLiveEvent
import com.bvc.ordering.ksnet.Telegram
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
) : BaseViewModel() {
    var liveData_Res: MutableLiveData<YoEntity.Res> = MutableLiveData<YoEntity.Res>()

    private val _requestTelegram = SingleLiveEvent<ByteArray>()
    val requestTelegram: LiveData<ByteArray> get() = _requestTelegram

    private val _affiliate = SingleLiveEvent<String>().apply {
        value = ""
    }
    val affiliate: LiveData<String> get() = _affiliate

    private val _affiliateName = SingleLiveEvent<String>().apply {
        value = "로그인"
    }
    val affiliateName: LiveData<String> get() = _affiliateName

    private val _startVisible = SingleLiveEvent<Boolean>().apply {
        value = false
    }
    val startVisible: LiveData<Boolean> get() = _startVisible

    init {

    }


    fun onSplashClick() {
        _requestTelegram.value = Telegram.makeTelegramIC(
            apprCode = "1",
            mDeviceNo = "DPT0TEST03",
            quota = "00",
            totAmt = "1004",
            orgApprNo = "123456789012",
            orgDate = "201020"
        )
    }

    fun requsetUsers() {
//        liveData_Res = getUserUseCase.invoke("shop",)
        getUserUseCase("shop", viewModelScope) {
            liveData_Res.postValue(it)
        }
//        CoroutineScope(Dispatchers.IO).launch {
//            val response = repo.users("shop")
//            if (response.isSuccessful) {
//                response.body()?.let { setLike(it) }
//            } else {
//                var handler = android.os.Handler(Looper.getMainLooper())
//                com.droi.util.Util.showToast("${response.code()} ${response.message()}")
//            }
//        }
    }
}
