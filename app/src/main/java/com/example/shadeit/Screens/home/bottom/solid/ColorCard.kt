package com.example.shadeit.Screens.home.bottom.solid

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.example.shadeit.database.Colors

@Composable
fun SolidColorCard(
    color: Colors,
    isSelected: Boolean = false,
    onLongPress: (Colors) -> Unit,
    onClick: (Colors) -> Unit = {}
) {

    val clipboardManager = LocalClipboardManager.current

    Card(
        modifier = Modifier
            .aspectRatio(2.5f)
            .padding(1.dp)
            .pointerInput(color) {
                detectTapGestures(
                    onLongPress = {
                        onLongPress(color)
                    },
                    onTap = {
                        onClick(color)
                    }
                )
            },
        colors = CardDefaults.cardColors(Color(color.color.toColorInt())),
        shape = RoundedCornerShape(12.dp),
        border = if(isSelected) { BorderStroke(2.dp, Color.Red) } else null
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                TextButton(onClick = {
                    if (!isSelected) {
                        clipboardManager.setText(AnnotatedString(color.color))
                    } else {
                        onClick(color)
                    }
                }) {
                    Text(
                        text = color.color,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 14.sp,
                        color = White,
                    )
                }
            }

            Column(modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Bottom) {
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Selected",
                        tint = White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewColorCard() {
    SolidColorCard( Colors(id = 0, color = "#FFAABB", firestoreId = "", timestamp = "12/05/2023".toLong()), onLongPress = {} )
}