package com.example.transportapp.main.routes.presentation.components

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import com.yandex.runtime.image.ImageProvider

fun createCircleMarker(color: Int, radius: Float): ImageProvider {
    val size = (radius * 2).toInt()
    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        this.color = color
    }

    val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        this.color = android.graphics.Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 2f
    }

    canvas.drawCircle(radius, radius, radius - 2f, paint)
    canvas.drawCircle(radius, radius, radius - 2f, strokePaint)

    return ImageProvider.fromBitmap(bitmap)
}