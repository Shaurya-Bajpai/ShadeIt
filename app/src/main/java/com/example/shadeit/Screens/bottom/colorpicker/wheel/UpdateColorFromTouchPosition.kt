package com.example.shadeit.Screens.bottom.colorpicker.wheel

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import com.example.shadeit.Screens.bottom.colorpicker.PickerViewModel
import kotlin.math.atan2
import kotlin.math.hypot

fun UpdateColorFromTouchPosition(viewModel: PickerViewModel, size: Size, offset: Offset) {
    val centerX = size.width / 2f
    val centerY = size.height / 2f
    val dx = offset.x - centerX
    val dy = offset.y - centerY
    val distance = hypot(dx, dy) / (size.width / 2f)

    if (distance <= 1f) {
        val angle = atan2(dy, dx)
        val hue = (Math.toDegrees(angle.toDouble()) + 360) % 360

        val brightness = if (viewModel.isEditingFirstColor.value) {
            viewModel.firstBrightness.floatValue
        } else {
            viewModel.secondBrightness.floatValue
        }

        val hueColor = hue.toFloat()
        val saturation = distance.coerceIn(0f, 1f)

        if (viewModel.isEditingFirstColor.value) {
            viewModel.firstHue.floatValue = hueColor
            viewModel.firstSaturation.floatValue = saturation
            viewModel.isFirstColorInitialized.value = true
        } else {
            viewModel.secondHue.floatValue = hueColor
            viewModel.secondSaturation.floatValue = saturation
            viewModel.isSecondColorInitialized.value = true
        }

        viewModel.setActiveColor(
            Color.hsv(
                hue = hueColor,
                saturation = saturation,
                value = brightness
            )
        )
    }
}
