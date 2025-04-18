package com.bvc.ordering.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.bvc.ordering.R

class CircularDonutView
    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
    ) : View(context, attrs) {
        var progress = 0f // 0f ~ 1f
            set(value) {
                field = value
                invalidate()
            }

        private val paintBackground =
            Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.STROKE
                strokeWidth = context.resources.getDimensionPixelSize(R.dimen.d_700).toFloat()
                color = context.getColor(R.color.bvc_3317C2C9)
            }

        private val paintProgress =
            Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.STROKE
                strokeWidth = context.resources.getDimensionPixelSize(R.dimen.d_700).toFloat()
                color = context.getColor(R.color.bvc_17C2C9)
                strokeCap = Paint.Cap.ROUND
            }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)

            val padding = 15f
            val radius = (width.coerceAtMost(height) / 2f) - padding

            val centerX = width / 2f
            val centerY = height / 2f
            val rect =
                RectF(
                    centerX - radius,
                    centerY - radius,
                    centerX + radius,
                    centerY + radius,
                )

            // 배경 원
            canvas.drawArc(rect, 0f, 360f, false, paintBackground)

            // 진행 원
            canvas.drawArc(rect, -90f, progress * 360f, false, paintProgress)
        }
    }
