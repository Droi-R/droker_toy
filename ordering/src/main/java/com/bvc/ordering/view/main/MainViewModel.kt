package com.bvc.ordering.view.main

import com.bvc.domain.usecase.GetUserRepoUseCase
import com.bvc.ordering.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val getUserUseCase: GetUserRepoUseCase,
    ) : BaseViewModel() {
        companion object {
//            const val MIllIS_IN_FUTURE = 30000L
            const val TICK_INTERVAL = 1000L
        }

//        var liveData_Res: MutableLiveData<YoEntity.Res> = MutableLiveData<YoEntity.Res>()
        var change: Int = -1

//        val customTimerDuration: MutableLiveData<Long> = MutableLiveData(MIllIS_IN_FUTURE)
        var oldTimeMills: Long = 0

        fun requsetUsers() {
//        liveData_Res = getUserUseCase.invoke("shop",)
//            getUserUseCase("shop", viewModelScope) {
//                liveData_Res.postValue(it)
//            }
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

//        val timerJob: Job =
//            viewModelScope.launch(start = CoroutineStart.LAZY) {
//                withContext(Dispatchers.IO) {
//                    oldTimeMills = System.currentTimeMillis()
//                    while (customTimerDuration.value!! > 0L) {
//                        val delayMills = System.currentTimeMillis() - oldTimeMills
//                        if (delayMills == TICK_INTERVAL) {
//                            customTimerDuration.postValue(customTimerDuration.value!! - delayMills)
//                            oldTimeMills = System.currentTimeMillis()
//                        }
//                    }
//                }
//            }
//
//        fun setLike(body: YoEntity.Res) {
//            val db = AppDatabase.getInstance(App.getInstance())
//            for ((i, b) in body.items.withIndex()) {
//                val result = db?.contactsDao()?.findByResult(b.id)
//                if (result != null) {
//                    b.like = true
//                    body.items[i] = b
//                }
//            }
//            liveData_Res.postValue(body)
//        }
//
//        fun isLike(position: Int) {
//            val db = AppDatabase.getInstance(App.getInstance())
//            val res = liveData_Res.value
//            val item = res?.items?.get(position)
//            if (item != null) {
//                val result = db?.contactsDao()?.findByResult(item.id)
//                Logger.loge("result   $result")
//                if (result == null) {
//                    db?.contactsDao()?.insert(Contacts(0, item.id))
//                    item.like = true
//                } else {
//                    db.contactsDao().delete(result)
//                    item.like = false
//                }
//                res.items[position] = item
//                change = position
//                liveData_Res.postValue(res!!)
//            }
//        }
    }
