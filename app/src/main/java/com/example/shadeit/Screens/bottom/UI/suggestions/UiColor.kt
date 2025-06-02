package com.example.shadeit.Screens.bottom.UI.suggestions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shadeit.Screens.bottom.UI.dataclass.UISuggestedColor

@Composable
fun UiColors(colors: UISuggestedColor) {
    val clipboardManager = LocalClipboardManager.current
    val pairedColors = colors.image_based.zip(colors.description_based)
    val ColorSize = pairedColors.size

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(8.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
//            containerColor = Brush.verticalGradient(
//                colors = listOf(Color(0xFF1F1F21), Color(0xFF2C2C2E))
//            ).toColor(),
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "UI Colors",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp, bottom = 12.dp)
            )

            Divider(
                thickness = 1.dp,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, bottom = 16.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color.DarkGray, Color.Gray, Color.DarkGray)
                        )
                    ),
                color = Color.Transparent
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                // Image-Based Colors Column
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        "🎨  Used",
                        color = Color.LightGray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    pairedColors.forEach { (imageColor, _) ->
                        ColorChip(hex = imageColor, clipboardManager)
                    }
                }

                // Vertical Divider
                Box(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .width(1.dp)
                        .height(((ColorSize/2) * 110).dp)
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.DarkGray, Color.Gray, Color.DarkGray)
                            )
                        )
                )

                // AI-Suggested Colors Column
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        "🤖  Suggested",
                        color = Color.LightGray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    pairedColors.forEach { (_, aiColor) ->
                        ColorChip(hex = aiColor, clipboardManager)
                    }
                }
            }
        }
    }
}

@Composable
fun ColorChip(hex: String, clipboardManager: ClipboardManager) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF3A3A3C))
            .padding(horizontal = 10.dp, vertical = 8.dp)
            .clickable {
                clipboardManager.setText(AnnotatedString(hex))
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(Color(android.graphics.Color.parseColor(hex)))
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column {
            Text(
                text = hex,
                fontFamily = FontFamily.Monospace,
                fontSize = 14.sp,
                color = Color.White
            )
        }
    }
}
