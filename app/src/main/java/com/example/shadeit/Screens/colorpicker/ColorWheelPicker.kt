package com.example.shadeit.Screens.colorpicker

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shadeit.Screens.colorpicker.wheel.CreateColorWheelBitmap
import com.example.shadeit.Screens.colorpicker.wheel.UpdateColorFromTouchPosition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ColorPicker(viewModel: PickerViewModel) {

    val context = LocalContext.current
    val wheelSize = 200.dp
    var isDragging by remember { mutableStateOf(false) }
    val wheelBitmap = remember { mutableStateOf<ImageBitmap?>(null) }
    val density = LocalDensity.current.density
    var showGradientDropdown by remember { mutableStateOf(false) }
    var showSolidDropdown by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }

    // Generate the color wheel bitmap in a background thread
    LaunchedEffect(Unit) {
        loading = true
        withContext(Dispatchers.Default) {
            wheelBitmap.value = CreateColorWheelBitmap(wheelSize, density)
        }
        loading = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Gradient preview
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if(viewModel.isFirstColorInitialized.value) {
                            if(viewModel.isSecondColorInitialized.value){
                                showGradientDropdown = !showGradientDropdown
                                showSolidDropdown = false
                            } else {
                                Toast.makeText(context, "Select second color", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "Select your color", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .height(60.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                viewModel.firstColor.value.copy(alpha = viewModel.firstAlpha.floatValue),
                                viewModel.secondColor.value.copy(alpha = viewModel.secondAlpha.floatValue)
                            )
                        ),
                        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                    )
                    .border(1.dp, Color.Gray, RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            )

            AnimatedVisibility(
                visible = showGradientDropdown,
                enter = slideInVertically(initialOffsetY = { -40 }) + expandVertically(expandFrom = Alignment.Top) + scaleIn(transformOrigin = TransformOrigin(0.5f, 0f)) + fadeIn(initialAlpha = 0.3f),
                exit = slideOutVertically(targetOffsetY = { -40 }) + shrinkVertically(shrinkTowards = Alignment.Top) + scaleOut(transformOrigin = TransformOrigin(0.5f, 0f)) + fadeOut(),
                modifier = Modifier.fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                    )
            ) {
                val AlphaFirstColor = getHexColorCode(viewModel.firstHue.floatValue, viewModel.firstSaturation.floatValue, viewModel.firstBrightness.floatValue, viewModel.firstAlpha.floatValue)
                val AlphaSecondColor = getHexColorCode(viewModel.secondHue.floatValue, viewModel.secondSaturation.floatValue, viewModel.secondBrightness.floatValue, viewModel.secondAlpha.floatValue)

                Column(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.fillMaxWidth().weight(1f)) {
                            PickerColorElement(viewModel.firstColor.value.toHex())
                            PickerColorElement(AlphaFirstColor)
                        }
                        Spacer(Modifier.height(2.dp))
                        Column(modifier = Modifier.fillMaxWidth().weight(1f)) {
                            PickerColorElement(viewModel.secondColor.value.toHex())
                            PickerColorElement(AlphaSecondColor)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Color wheel
            Box(
                modifier = Modifier
                    .size(wheelSize)
            ) {
                wheelBitmap.value?.let { bitmap ->
                    Image(
                        bitmap = bitmap,
                        contentDescription = "Color Wheel",
                        modifier = Modifier
                            .fillMaxSize()
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onTap = { offset ->
                                        UpdateColorFromTouchPosition(
                                            viewModel = viewModel,
                                            size = Size(wheelSize.toPx(), wheelSize.toPx()),
                                            offset = offset
                                        )
                                    },
                                    onPress = { offset ->
                                        isDragging = true
                                        UpdateColorFromTouchPosition(
                                            viewModel = viewModel,
                                            size = Size(wheelSize.toPx(), wheelSize.toPx()),
                                            offset = offset
                                        )
                                        tryAwaitRelease()
                                        isDragging = false
                                    }
                                )
                            }
                            .pointerInput(Unit) {
                                awaitPointerEventScope {
                                    while (true) {
                                        val event = awaitPointerEvent()
                                        if (isDragging && event.changes.isNotEmpty()) {
                                            val offset = event.changes.first().position
                                            UpdateColorFromTouchPosition(
                                                viewModel = viewModel,
                                                size = Size(wheelSize.toPx(), wheelSize.toPx()),
                                                offset = offset
                                            )
                                        }
                                    }
                                }
                            }
                    )
                }
            }

            // Type of Color
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    if (viewModel.isGradientMode.value) "Gradient Colors" else "Solid Color",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 14.sp
                )
                Switch(
                    checked = viewModel.isGradientMode.value,
                    onCheckedChange = { viewModel.isGradientMode.value = it },
                    modifier = Modifier.padding(horizontal = 8.dp).size(60.dp),
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.DarkGray,
                        uncheckedThumbColor = Color.DarkGray,
                        checkedTrackColor = Color.Transparent,
                        uncheckedTrackColor = Color.Transparent,
                        checkedBorderColor = Color.DarkGray,
                        uncheckedBorderColor = Color.DarkGray
                    ),
                    thumbContent = {
                        if(viewModel.isGradientMode.value) Text("G", color = Color.White, fontSize = 10.sp) else Text("S", color = Color.White, fontSize = 10.sp)
                    }
                )
            }

            if (viewModel.isGradientMode.value) {
                // Gradient mode - show two color buttons and a gradient preview
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // First color button
                    ColorButton(
                        color = viewModel.firstColor.value,
                        isSelected = viewModel.isEditingFirstColor.value,
                        onClick = { viewModel.isEditingFirstColor.value = true },
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    // Second color button
                    ColorButton(
                        color = viewModel.secondColor.value,
                        isSelected = !viewModel.isEditingFirstColor.value,
                        onClick = { viewModel.isEditingFirstColor.value = false },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            } else {
                // Single color mode - show only the selected color
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if(viewModel.isFirstColorInitialized.value) {
                                if(viewModel.isSecondColorInitialized.value && !showGradientDropdown) {
                                    Toast.makeText(context, "Open Gradient Box", Toast.LENGTH_SHORT).show()
                                } else if(!viewModel.isSecondColorInitialized.value) {
                                    showSolidDropdown = !showSolidDropdown
                                }
                            } else {
                                Toast.makeText(context, "Select your color", Toast.LENGTH_SHORT).show()
                            }
                        }
                        .height(50.dp)
                        .background(viewModel.firstColor.value.copy(alpha = viewModel.firstAlpha.floatValue), shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                        .border(0.5.dp, Color.Gray, RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                )

                AnimatedVisibility(
                    visible = showSolidDropdown,
                    enter = slideInVertically(initialOffsetY = { -40 }) + expandVertically(expandFrom = Alignment.Top) + scaleIn(transformOrigin = TransformOrigin(0.5f, 0f)) + fadeIn(initialAlpha = 0.3f),
                    exit = slideOutVertically(targetOffsetY = { -40 }) + shrinkVertically(shrinkTowards = Alignment.Top) + scaleOut(transformOrigin = TransformOrigin(0.5f, 0f)) + fadeOut(),
                    modifier = Modifier.fillMaxWidth()
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                        )
                ) {
                    val AlphaFirstColor = getHexColorCode(viewModel.firstHue.floatValue, viewModel.firstSaturation.floatValue, viewModel.firstBrightness.floatValue, viewModel.firstAlpha.floatValue)

                    Column(
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            PickerColorElement(viewModel.firstColor.value.toHex())
                            PickerColorElement(AlphaFirstColor)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Brightness slider
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Brightness", color = Color.White, fontSize = 14.sp)
                    Text(text = if (viewModel.isEditingFirstColor.value) "${(viewModel.firstBrightness.floatValue * 100).toInt()}%" else "${(viewModel.secondBrightness.floatValue * 100).toInt()}%", color = Color.White, fontSize = 14.sp)
                }

                Slider(
                    value = if (viewModel.isEditingFirstColor.value) viewModel.firstBrightness.floatValue else viewModel.secondBrightness.floatValue,
                    onValueChange = { brightness ->
                        if (viewModel.isEditingFirstColor.value) {
                            viewModel.firstBrightness.floatValue = brightness
                            if (viewModel.isFirstColorInitialized.value) {
                                viewModel.firstColor.value = Color.hsv(
                                    hue = viewModel.firstHue.floatValue,
                                    saturation = viewModel.firstSaturation.floatValue,
                                    value = brightness
                                )
                            } else {
                                Toast.makeText(context, "Select your color", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            viewModel.secondBrightness.floatValue = brightness
                            if (viewModel.isSecondColorInitialized.value) {
                                viewModel.secondColor.value = Color.hsv(
                                    hue = viewModel.secondHue.floatValue,
                                    saturation = viewModel.secondSaturation.floatValue,
                                    value = brightness
                                )
                            } else {
                                Toast.makeText(context, "Select your color", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    colors = SliderDefaults.colors(
                        thumbColor = Color.White,
                        activeTrackColor = Color.Transparent,
                        inactiveTrackColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color.Black, Color.LightGray)
                            ),
                            shape = RoundedCornerShape(50)
                        )
                        .border(width = 1.dp, color = Color.DarkGray.copy(alpha = 0.4f), shape = RoundedCornerShape(50))
                        .height(18.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Opacity slider
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Opacity", color = Color.White, fontSize = 14.sp)
                    Text(text = if(viewModel.isEditingFirstColor.value) "${(viewModel.firstAlpha.floatValue * 100).toInt()}%" else "${(viewModel.secondAlpha.floatValue * 100).toInt()}%", color = Color.White, fontSize = 14.sp)
                }

                Slider(
                    value = if(viewModel.isEditingFirstColor.value) viewModel.firstAlpha.floatValue else viewModel.secondAlpha.floatValue,
                    onValueChange = {
                        if (viewModel.isEditingFirstColor.value) {
                            viewModel.firstAlpha.floatValue = it
                        } else {
                            viewModel.secondAlpha.floatValue = it
                        }
                    },
                    colors = SliderDefaults.colors(
                        thumbColor = viewModel.getActiveColor().value.copy(alpha = 1f),
                        activeTrackColor = Color.Transparent,
                        inactiveTrackColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    viewModel.getActiveColor().value.copy(alpha = 0f),
                                    viewModel.getActiveColor().value.copy(alpha = 1f)
                                )
                            ),
                            shape = RoundedCornerShape(50)
                        )
                        .border(width = 1.dp, color = Color.DarkGray.copy(alpha = 0.4f), shape = RoundedCornerShape(50))
                        .height(18.dp)
                )
            }
        }
    }
}


fun getHexColorCode(hue: Float, saturation: Float, brightness: Float, alpha: Float): String {
    val color = Color.hsv(hue, saturation, brightness).copy(alpha = alpha)

    val a = (color.alpha * 255).toInt()
    val r = (color.red * 255).toInt()
    val g = (color.green * 255).toInt()
    val b = (color.blue * 255).toInt()

    return String.format("#%02X%02X%02X%02X", a, r, g, b)
}

private fun Color.toHex(): String {
    return String.format("#%02X%02X%02X", (red * 255).toInt(), (green * 255).toInt(), (blue * 255).toInt())
}
