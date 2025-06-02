package com.example.shadeit.Screens.bottom.colorpicker

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

class PickerViewModel : ViewModel() {
    var selectedImageUri = mutableStateOf<Uri?>(null)
    var imageBitmap = mutableStateOf<Bitmap?>(null)

    var firstColor = mutableStateOf(Color.DarkGray)
    var firstAlpha = mutableFloatStateOf(1f)
    var firstBrightness = mutableFloatStateOf(1f)
    var firstHue = mutableFloatStateOf(0f)
    var firstSaturation = mutableFloatStateOf(1f)
    var isFirstColorInitialized = mutableStateOf(false)

    var secondColor = mutableStateOf(Color.DarkGray)
    var secondAlpha = mutableFloatStateOf(1f)
    var secondBrightness = mutableFloatStateOf(1f)
    var secondHue = mutableFloatStateOf(0f)
    var secondSaturation = mutableFloatStateOf(1f)
    var isSecondColorInitialized = mutableStateOf(false)

    var isEditingFirstColor = mutableStateOf(true)
    var isGradientMode = mutableStateOf(false)


    // Returns the currently active color (either first or second based on isEditingFirstColor)
    fun getActiveColor(): MutableState<Color> {
        return if (isEditingFirstColor.value) firstColor else secondColor
    }

    // Sets the currently active color
    fun setActiveColor(color: Color) {
        if (isEditingFirstColor.value) {
            firstColor.value = color
        } else {
            secondColor.value = color
        }
    }
}