package com.example.shadeit.Screens.bottom.colorpicker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PickerColorElement(
    colorCode: String,
    modifier: Modifier = Modifier,
) {
    val clipboardManager = LocalClipboardManager.current
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.clickable { clipboardManager.setText(AnnotatedString(colorCode)) }
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(
                        try {
                            Color(android.graphics.Color.parseColor(colorCode))
                        } catch (e: IllegalArgumentException) {
                            Color.Gray
                        },
                        shape = CircleShape
                    )
            )
            // Arrow
            Text("→", fontSize = 16.sp, color = Color.Black)
            // Color Code
            Text(text = colorCode, modifier = Modifier.clickable { clipboardManager.setText(AnnotatedString(colorCode)) }, fontSize = 16.sp, color = Color.Black)
        }
    }
}