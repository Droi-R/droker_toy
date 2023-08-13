package com.droi

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.droi.callback.OnItemClick
import com.google.gson.Gson

abstract class BaseActivity :
    AppCompatActivity(),
    View.OnClickListener,
    OnItemClick {

    var gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _init()
    }

    abstract fun _init()

    override fun onClick(v: View) {
    }
    override fun oneClick(v: View, position: Int) {
    }
}
