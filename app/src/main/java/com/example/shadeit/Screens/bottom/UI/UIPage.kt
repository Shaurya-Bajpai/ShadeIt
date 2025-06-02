package com.example.shadeit.Screens.bottom.UI

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.shadeit.navigation.Screen
import com.example.shadeit.viewmodel.ColorViewModel

@Composable
fun UIPage(
    navController: NavController,
    viewModel: ColorViewModel
) {

    val uiColors by viewModel.uiImages.collectAsState()
    val selectedColors = viewModel.selectedUIColors
    val isSelectionModeActive by viewModel.isUISelectionModeActive

    // Handle long press
    val handleLongPress: (UIUpload) -> Unit = { ui ->
        viewModel.toggleUISelection(ui)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.TopCenter
    ) {
        if (uiColors.isEmpty()) {

            Column(
//                modifier = Modifier.padding(vertical = 20.dp).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Empty State",
                    modifier = Modifier.size(100.dp),
                    tint = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "UI Suggestions",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "No UI colors generated yet. Tap the + button to start!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center
                )

            }
        }

        else {
            LazyColumn(
                contentPadding = PaddingValues(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(uiColors) { ui ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .pointerInput(ui) {
                                detectTapGestures(
                                    onLongPress = {
                                        handleLongPress(ui)
                                    },
                                    onTap = {
                                        if (isSelectionModeActive) {
                                            viewModel.toggleUISelection(ui)
                                        } else {
                                            viewModel.selectedUIUpload = ui
                                            navController.navigate(Screen.UISuggestion.route)
                                        }
                                    }
                                )
                            },
                        shape = RoundedCornerShape(12.dp),
                        border = if(selectedColors.contains(ui)) { BorderStroke(2.dp, Color.Red) } else null,
                    ) {
                        Box(
                            contentAlignment = Alignment.CenterStart,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.DarkGray)
                                .padding(8.dp)
                        ) {
                            val scrollState = rememberScrollState()
                            AsyncImage(
                                model = ui.imageUrl,
                                contentDescription = "random image",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .verticalScroll(scrollState),
                                contentScale = ContentScale.FillHeight
                            )
                        }
                    }
                }
            }
        }
    }
}