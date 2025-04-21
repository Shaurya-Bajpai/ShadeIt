package com.example.shadeit.Screens.home.bottom.solid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.shadeit.database.Colors
import com.example.shadeit.viewmodel.ColorViewModel

@Composable
fun SolidPage(viewModel: ColorViewModel) {

    val colors by viewModel.Solidcolors.collectAsState()
    val selectedColors = viewModel.selectedSolidColors
    val isSelectionModeActive by viewModel.isSolidSelectionModeActive

    // Handle long press
    val handleLongPress: (Colors) -> Unit = { color ->
        viewModel.toggleSolidColorSelection(color)
    }

    // Handle click based on selection mode
    val handleClick: (Colors) -> Unit = { color ->
        if (isSelectionModeActive) {
            // In selection mode, toggle selection
            viewModel.toggleSolidColorSelection(color)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.TopCenter
    ) {
        if (colors.isEmpty()) {

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
                    text = "Solid Colors",
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

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(14.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(colors) { color ->
                    SolidColorCard(
                        color = color,
                        isSelected = selectedColors.contains(color),
                        onLongPress = handleLongPress,
                        onClick = handleClick
                    )
                }
            }

        }
    }
}