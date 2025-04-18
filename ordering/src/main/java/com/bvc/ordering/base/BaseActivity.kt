package com.bvc.ordering.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bvc.ordering.callback.OnItemClick
import com.google.gson.Gson

abstract class BaseActivity :
    AppCompatActivity(),
    View.OnClickListener,
    OnItemClick {
    var gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)
    }

    abstract fun init(savedInstanceState: Bundle?)

    override fun onClick(v: View) {
    }

    override fun oneClick(
        v: View,
        position: Int,
    ) {
    }
}
