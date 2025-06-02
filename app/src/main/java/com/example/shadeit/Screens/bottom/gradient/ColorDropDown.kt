package com.example.shadeit.Screens.bottom.gradient

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ColorDropDown(colorStops: List<String>) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    Column {

        colorStops.chunked(2).forEach { rowStops ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                rowStops.forEach { colorCode ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                clipboardManager.setText(AnnotatedString(colorCode))
                                Toast.makeText(context, "Copied $colorCode", Toast.LENGTH_SHORT)
                                    .show()
                            }
                    ) {
                        // Color Circle
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
                        Text(
                            text = colorCode,
                            modifier = Modifier.clickable {
                                clipboardManager.setText(
                                    AnnotatedString(
                                        colorCode
                                    )
                                )
                            },
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    }
                }

            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DropColorCard() {
    ColorDropDown(listOf("#FF0000", "#00FF00", "#000000"))
}