package com.example.shadeit.Screens.home.bottom.UI

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.shadeit.navigation.Screen
import com.example.shadeit.ui.theme.brush
import com.example.shadeit.viewmodel.ColorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UISuggestion(navController: NavController, viewModel: ColorViewModel) {
    var clipboardManager = LocalClipboardManager.current
    val isLoading by viewModel.isLoading.collectAsState()
    val UIsuggestedColors by viewModel.UIsuggestedColors.collectAsState()
    val uiUpload = viewModel.selectedUIUpload

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
        navController.navigate("${Screen.HomeScreen.route}?pageState=2") {
            popUpTo(Screen.HomeScreen.route) { inclusive = true }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Upload UI", fontSize = 22.sp)
                },
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
                .background(Color.Black)
                .padding(vertical = 12.dp, horizontal = 16.dp)
        ) {
            if (isLoading) {
            }
            else {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(it)
                        .size(225.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(12.dp)),
                ) {
                    AsyncImage(
                        model = uiUpload.imageUrl,
                        contentDescription = "random image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(Modifier.height(16.dp))

                UIsuggestedColors?.let { colors ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                        ) {

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                            ) {

                                Text("UI Colors", color = Color.White)

                                Spacer(Modifier.height(8.dp))

                                colors.image_based.forEach { color ->

                                    TextButton(onClick = {
                                        clipboardManager.setText(
                                            AnnotatedString(
                                                color
                                            )
                                        )
                                    }) {
                                        Text(
                                            text = color,
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 14.sp,
                                            color = Color.White,
                                            modifier = Modifier.padding(4.dp)
                                        )
                                    }
                                }

                            }

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                            ) {

                                Text("UI Color Suggestions", color = Color.White)

                                Spacer(Modifier.height(8.dp))

                                UIsuggestedColors?.description_based?.forEach { color ->

                                    TextButton(onClick = {
                                        clipboardManager.setText(
                                            AnnotatedString(
                                                color
                                            )
                                        )
                                    }) {
                                        Text(
                                            text = color,
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 14.sp,
                                            color = Color.White,
                                            modifier = Modifier.padding(4.dp)
                                        )
                                    }
                                }

                            }
                        }
                    }

                }
            }


            Spacer(Modifier.height(32.dp))

        }

    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewUIColorCard() {
//    UISuggestion(rememberNavController(), bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888), viewModel = ColorViewModel())
//}