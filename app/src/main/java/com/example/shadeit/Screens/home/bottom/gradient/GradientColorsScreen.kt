package com.example.shadeit.Screens.home.bottom.gradient

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.shadeit.viewmodel.ColorViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradientColorsScreen(viewModel: ColorViewModel) {

    val gradientColors by viewModel.GradientColors.collectAsState()

    val isGradientModeActive by viewModel.isGradientSelectionModeActive
    var expandedCard by remember { mutableStateOf<GradientColor?>(null) }
    val selectedColors = viewModel.selectedGradientColors

    // Handle long press
    val handleLongPress: (GradientColor) -> Unit = { color ->
        viewModel.toggleGradientColorSelection(color)
    }

    // Handle click based on selection mode
    val handleClick: (GradientColor) -> Unit = { color ->
        if (isGradientModeActive) { // In selection mode, toggle selection
            viewModel.toggleGradientColorSelection(color)
        } else { // In normal mode, expand/collapse the card
            if (expandedCard == color) {
                expandedCard = null
            } else {
                expandedCard = color
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(8.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        if (gradientColors.isEmpty()) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                //            modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Empty State",
                    modifier = Modifier.size(100.dp),
                    tint = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Gradient Colors",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "No colors generated yet. Tap the + button to start!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center
                )

            }
        }

        else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.TopCenter
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(4.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(gradientColors) { color ->
                        Column {

                            GradientColorCard(
                                gradientColor = color,
                                isSelected = selectedColors.contains(color),
                                onLongPress = handleLongPress,
                                onClick = handleClick
                            )

                            AnimatedVisibility(
                                visible = expandedCard == color,
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
                                        color = Color.White,
                                        shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                                    )
                            ) {
                                Column(
                                    modifier = Modifier.padding(top = 8.dp, start = 24.dp, end = 24.dp),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    ColorDropDown(color.colorStops)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}