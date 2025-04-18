package com.bvc.ordering.ui

import android.view.View
import androidx.databinding.BindingAdapter

object CommonBindingAdapters {
    @BindingAdapter(value = ["gone_visibility"], requireAll = true)
    @JvmStatic
    fun setViewVisibilityGone(
        view: View,
        visible: Boolean?,
    ) {
        view.visibility = if (visible == true) View.VISIBLE else View.GONE
    }
}
