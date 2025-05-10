package com.example.shadeit.Screens.colorpicker.wheel

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.Dp
import kotlin.math.atan2
import kotlin.math.hypot

fun CreateColorWheelBitmap(size: Dp, density: Float): ImageBitmap {
    val sizePx = (size.value * density).toInt()
    val bitmap = Bitmap.createBitmap(sizePx, sizePx, Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas(bitmap)
    val paint = android.graphics.Paint()

    val radius = sizePx / 2f
    val centerX = radius
    val centerY = radius

    for (y in 0 until sizePx) {
        for (x in 0 until sizePx) {
            val dx = x - centerX
            val dy = y - centerY
            val distance = hypot(dx, dy) / radius

            if (distance <= 1f) {
                val angle = Math.toDegrees(atan2(dy, dx).toDouble()).toFloat()
                val hue = (angle + 360) % 360
                val saturation = distance

                val color = android.graphics.Color.HSVToColor(
                    floatArrayOf(hue, saturation, 1f)
                )
                paint.color = color
                canvas.drawPoint(x.toFloat(), y.toFloat(), paint)
            }
        }
    }

    return bitmap.asImageBitmap()
}