package com.droi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.droi.App
import com.droi.data.util.Logger
import com.droi.db.AppDatabase
import com.droi.db.Contacts
import com.droi.domain.model.YoEntity
import com.droi.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
) : BaseViewModel() {
    var liveData_Res: MutableLiveData<YoEntity.Res> = MutableLiveData<YoEntity.Res>()
    var change: Int = -1
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

    fun setLike(body: YoEntity.Res) {
        val db = AppDatabase.getInstance(App.getInstance())
        for ((i, b) in body.items.withIndex()) {
            val result = db?.contactsDao()?.findByResult(b.id)
            if (result != null) {
                b.like = true
                body.items[i] = b
            }
        }
        liveData_Res.postValue(body)
    }

    fun isLike(position: Int) {
        val db = AppDatabase.getInstance(App.getInstance())
        val res = liveData_Res.value
        val item = res?.items?.get(position)
        if (item != null) {
            val result = db?.contactsDao()?.findByResult(item.id)
            Logger.loge("result   $result")
            if (result == null) {
                db?.contactsDao()?.insert(Contacts(0, item.id))
                item.like = true
            } else {
                db.contactsDao().delete(result)
                item.like = false
            }
            res.items[position] = item
            change = position
            liveData_Res.postValue(res!!)
        }
    }
}
