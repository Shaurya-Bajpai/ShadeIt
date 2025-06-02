package com.example.shadeit.Screens.bottom.UI.suggestions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shadeit.Screens.bottom.UI.dataclass.UISuggestedColor
import com.example.shadeit.Screens.bottom.UI.suggestions.palette.PaletteHex
import com.example.shadeit.Screens.bottom.UI.suggestions.palette.PaletteText

@Composable
fun UiComponent(colors: UISuggestedColor) {
    val clipboardManager = LocalClipboardManager.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(8.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "UI Components",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
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
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    PaletteText("🖲️ Button")
                    PaletteText("🖱️ Button Hover")
                    PaletteText("🏷️ Header")
                    PaletteText("🗂️ Card Background")
                    PaletteText("📏 Border")
                }

                // Vertical Divider
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .width(1.dp)
                        .height(235.dp)
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.DarkGray, Color.Gray, Color.DarkGray)
                            )
                        )
                )

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    PaletteHex(colors.organized_palette.ui_components.button, clipboardManager)
                    PaletteHex(colors.organized_palette.ui_components.button_hover, clipboardManager)
                    PaletteHex(colors.organized_palette.ui_components.header, clipboardManager)
                    PaletteHex(colors.organized_palette.ui_components.card_background, clipboardManager)
                    PaletteHex(colors.organized_palette.ui_components.border, clipboardManager)
                }
            }
        }
    }
}