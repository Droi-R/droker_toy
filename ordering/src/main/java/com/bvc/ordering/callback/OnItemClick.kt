package com.bvc.ordering.callback

import android.view.View

interface OnItemClick {
    fun oneClick(v : View, position : Int)
}