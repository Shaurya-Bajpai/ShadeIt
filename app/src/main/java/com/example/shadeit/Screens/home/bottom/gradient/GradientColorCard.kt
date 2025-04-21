package com.example.shadeit.Screens.home.bottom.gradient

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt

@Composable
fun GradientColorCard(
    gradientColor: GradientColor,
    isSelected: Boolean = false,
    onLongPress: (GradientColor) -> Unit,
    onClick: (GradientColor) -> Unit = {}
) {

    Card(
        modifier = Modifier
            .aspectRatio(5.6f)
            .pointerInput(gradientColor) {
                detectTapGestures(
                    onLongPress = {
                        onLongPress(gradientColor)
                    },
                    onTap = {
                        onClick(gradientColor)
                    }
                )
            }
            .background(
                brush = Brush.linearGradient(
                    colors = gradientColor.colorStops.map { Color(it.toColorInt()) }),
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        border = if(isSelected) { BorderStroke(2.dp, Color.Red) } else null,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().fillMaxSize().padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Column {
//                TextButton(onClick = {
//                    if (!isSelected) {
////                        val combinedColorCode = gradientColor.colorStops.joinToString(", ") { it.toColorInt().toString() }
////                        clipboardManager.setText(AnnotatedString(combinedColorCode))
//                        clipboardManager.setText(AnnotatedString(gradientColor.color))
//                    }
//                    else { onClick(gradientColor) }
//                }) {
//                    Text(
//                        text = gradientColor.color,
////                        text = gradientColor.colorStops.joinToString(", ") { it.toColorInt().toString() },
//                        fontFamily = FontFamily.Monospace,
//                        fontSize = 14.sp,
//                        color = White,
//                    )
//                }
                Icon(
                    imageVector = if(isSelected) Icons.Default.CheckCircle else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expand",
                    tint = White
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewGradientColorCard() {
    GradientColorCard(gradientColor = GradientColor(
        color = "#FF0000, #00FF00",
        colorStops = listOf("#FF0000", "#00FF00")
    ), onLongPress = {}, onClick = {})
}