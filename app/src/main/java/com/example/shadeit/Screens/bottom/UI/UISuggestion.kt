package com.example.shadeit.Screens.bottom.UI

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.shadeit.Screens.bottom.UI.suggestions.UiColors
import com.example.shadeit.Screens.bottom.UI.suggestions.OrganisedPalette
import com.example.shadeit.Screens.bottom.UI.suggestions.UiAdditionalNotes
import com.example.shadeit.Screens.bottom.UI.suggestions.UiColorAnalysis
import com.example.shadeit.Screens.bottom.UI.suggestions.UiComponent
import com.example.shadeit.navigation.Screen
import com.example.shadeit.ui.theme.brush
import com.example.shadeit.viewmodel.ColorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UISuggestion(navController: NavController, viewModel: ColorViewModel) {
    val scrollState = rememberScrollState()
    val isLoading by viewModel.isLoading.collectAsState()
    val UIsuggestedColors by viewModel.UIsuggestedColors.collectAsState()
    val uiUpload = viewModel.selectedUIUpload

    // Animate the scale of the image
    var scale by remember { mutableStateOf(1f) }
    val animatedScale by animateFloatAsState(targetValue = scale)

    if (uiUpload == null) {
        // Show loading indicator and AI-like message
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(color = Color.White)
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Analyzing your input... This might take a moment. Please wait.",
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
        return
    }

    LaunchedEffect(Unit) {
        // Use the Firestore ID to fetch suggested colors
        viewModel.fetchSuggestedColors(uiUpload.firestoreId)
        viewModel.fetchUIImage()
    }

    BackHandler {
        navController.navigate("${Screen.Controller.route}?pageState=3") {
            popUpTo(Screen.Controller.route) { inclusive = true }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Color Suggestion", fontSize = 22.sp) },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable(onClick = {
                                navController.navigate("${Screen.HomeScreen.route}?pageState=2") {
                                    popUpTo(Screen.HomeScreen.route) { inclusive = true }
                                }
                            })
                            .size(24.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White
                ),
                modifier = Modifier.background(brush)
            )
        },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(Color.Black)
                .padding(vertical = 12.dp, horizontal = 8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it)
                    .size(225.dp)
                    .border(1.dp, Color.DarkGray, RoundedCornerShape(12.dp)),
            ) {
                AsyncImage(
                    model = uiUpload.imageUrl,
                    contentDescription = "random image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .scale(animatedScale) // Apply the animated scale
                        .pointerInput(Unit) {
                            detectTransformGestures { _, _, zoom, _ ->
                                scale = (scale * zoom).coerceIn(1f, 4f) // Limit scale between 1x and 5x
                            }
                        },
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(Modifier.height(16.dp))

            UIsuggestedColors?.let { colors ->

                UiColors(colors) // Image and Description based colors

                UiColorAnalysis(colors) // UI Component colors

                OrganisedPalette(colors) // Organised Palette

                UiComponent(colors) // UI Component colors

                UiAdditionalNotes(colors) // Additional Notes

            }
        }
    }
}