package com.example.shadeit.Screens.home.bottom.UI.suggestions.colorAnalysis

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shadeit.Screens.home.bottom.UI.dataclass.Accent
import com.example.shadeit.Screens.home.bottom.UI.dataclass.Background
import com.example.shadeit.Screens.home.bottom.UI.dataclass.Primary
import com.example.shadeit.Screens.home.bottom.UI.dataclass.Secondary
import com.example.shadeit.Screens.home.bottom.UI.dataclass.Text

@Composable
fun AnalysisDropDown(colors: Any) {

    val colorUsed = when (colors) {
        is Primary -> colors.color
        is Secondary -> colors.color
        is Accent -> colors.color
        is Background -> colors.color
        is Text -> colors.color
        else -> "000000"
    }

    var colorSuggested = when (colors) {
        is Primary -> colors.suggested_color
        is Secondary -> colors.suggested_color
        is Accent -> colors.suggested_color
        is Background -> colors.suggested_color
        is Text -> colors.suggested_color
        else -> "000000"
    }

    var colorReason = when (colors) {
        is Primary -> colors.reason
        is Secondary -> colors.reason
        is Accent -> colors.reason
        is Background -> colors.reason
        is Text -> colors.reason
        else -> "000000"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        // Color Used
        AnalysisDetailColorCard("Color", colorUsed)

        Spacer(Modifier.height(4.dp))

        // Suggested Color
        if(colorSuggested != null)
            AnalysisDetailColorCard("Suggested", colorSuggested.toString())

        Spacer(Modifier.height(6.dp))

        // Reason
        AnalysisDetailReasonCard("Reason: ", colorReason)
    }
}

@Composable
fun AnalysisDetailColorCard(title: String, color: String) {
    val clipboardManager = LocalClipboardManager.current
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                color = White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1.5f).padding(start = 4.dp)
            )

            Text("→", fontSize = 17.sp, color = White, modifier = Modifier.weight(0.5f))

            Row(
                modifier = Modifier.weight(2f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(
                            try {
                                Color(android.graphics.Color.parseColor(color))
                            } catch (e: IllegalArgumentException) {
                                Color.Gray
                            },
                            shape = CircleShape
                        )
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = color,
                    modifier = Modifier.clickable {
                        clipboardManager.setText(AnnotatedString(color))
                    },
                    fontSize = 14.sp,
                    color = White
                )
            }
        }
    }
}

@Composable
fun AnalysisDetailReasonCard(title: String, color: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = title,
            color = White,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 4.dp)
        )

        Spacer(Modifier.width(4.dp))

        Text(
            text = color,
            textAlign = TextAlign.Justify,
            fontSize = 13.sp,
            color = White
        )
    }
}