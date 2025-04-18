package com.bvc.ordering.util

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DefaultResourceProvider
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : ResourceProvider {
        override fun getString(
            @StringRes resId: Int,
        ): String = context.getString(resId)

        override fun getString(
            @StringRes resId: Int,
            vararg args: Any,
        ): String = context.getString(resId, *args)

        override fun getColor(
            @ColorRes resId: Int,
        ): Int = ContextCompat.getColor(context, resId)

        override fun getDrawable(
            @DrawableRes resId: Int,
        ): Drawable? = ContextCompat.getDrawable(context, resId)
    }
