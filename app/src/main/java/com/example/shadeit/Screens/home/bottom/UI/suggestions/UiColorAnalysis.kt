package com.example.shadeit.Screens.home.bottom.UI.suggestions

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shadeit.Screens.home.bottom.UI.dataclass.ColorAnalysis
import com.example.shadeit.Screens.home.bottom.UI.dataclass.UISuggestedColor
import com.example.shadeit.Screens.home.bottom.UI.suggestions.colorAnalysis.AnalysisCard
import com.example.shadeit.Screens.home.bottom.UI.suggestions.colorAnalysis.AnalysisDropDown
import com.example.shadeit.Screens.home.bottom.UI.suggestions.palette.PaletteHex
import com.example.shadeit.Screens.home.bottom.UI.suggestions.palette.PaletteText
import com.example.shadeit.Screens.home.bottom.gradient.ColorDropDown
import com.example.shadeit.Screens.home.bottom.gradient.GradientColor
import com.example.shadeit.Screens.home.bottom.gradient.GradientColorCard

@Composable
fun UiColorAnalysis(colors: UISuggestedColor) {
    var expandedCard by remember { mutableStateOf<String?>(null) }

    val handleClick: (String) -> Unit = { title ->
        expandedCard = if (expandedCard == title) null else title
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(8.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "UI Color Analysis",
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

            Column {

                listOf(
                    "🔵 Primary" to colors.color_analysis.primary,
                    "⚫ Secondary" to colors.color_analysis.secondary,
                    "✨ Accent" to colors.color_analysis.accent,
                    "🧾 Background" to colors.color_analysis.background,
                    "✍️ Text" to colors.color_analysis.text
                ).forEach { (title, color) ->

                    AnalysisCard(
                        title = title,
                        handleClick = handleClick
                    )

                    AnimatedVisibility(
                        visible = expandedCard == title,
                        enter = slideInVertically(
                            // Start the slide from 40 (pixels) above where the content is supposed to go, to produce a parallax effect
                            initialOffsetY = { -40 }
                        ) + expandVertically(expandFrom = Alignment.Top) +
                                scaleIn(
                                    // Animate scale from 0f to 1f using the top center as the pivot point.
                                    transformOrigin = TransformOrigin(0.5f, 0f)
                                ) + fadeIn(initialAlpha = 0.3f),
                        exit = slideOutVertically(
                            targetOffsetY = { -40 } // Optional: slide up to mimic entry slide
                        ) + shrinkVertically(shrinkTowards = Alignment.Top) +
                                scaleOut(transformOrigin = TransformOrigin(0.5f, 0f)) +
                                fadeOut(),
                        modifier = Modifier.fillMaxWidth().fillMaxSize()
                            .background(
                                color = Color.DarkGray,
                                shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                            )
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            AnalysisDropDown(color)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                }
            }

        }
    }
}