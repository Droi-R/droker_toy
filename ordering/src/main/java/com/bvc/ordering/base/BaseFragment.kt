package com.bvc.ordering.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bvc.ordering.callback.OnItemClick
import com.google.gson.Gson

abstract class BaseFragment :
    Fragment(),
    View.OnClickListener,
    OnItemClick {

    var gson = Gson()
    override fun onStart() {
        super.onStart()
    }

    abstract fun _init()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _init()
    }

    override fun onClick(v: View) {
    }
}
