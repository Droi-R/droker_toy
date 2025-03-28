package com.bvc.ordering.ui

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

object CommonBindingAdapters {
    @BindingAdapter(value = ["gone_visibility"], requireAll = true)
    @JvmStatic
    fun setViewVisibilityGone(
        view: View,
        visible: Boolean?,
    ) {
        visible?.let {
            view.isVisible = it
        } ?: run {
            view.isVisible = false
        }
    }
}
