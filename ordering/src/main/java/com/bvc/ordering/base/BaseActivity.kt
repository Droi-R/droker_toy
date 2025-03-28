package com.bvc.ordering.base

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bvc.domain.log
import com.bvc.ordering.callback.OnItemClick
import com.bvc.ordering.ksnet.KsnetUtil
import com.bvc.ordering.ksnet.TransactionData
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
