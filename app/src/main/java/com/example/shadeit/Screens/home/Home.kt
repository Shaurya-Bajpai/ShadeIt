package com.example.shadeit.bottom

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.shadeit.R
import com.example.shadeit.Screens.colorpicker.PickerViewModel
import com.example.shadeit.Screens.colorpicker.ColorPicker
import com.example.shadeit.Screens.home.AddColor
import com.example.shadeit.Screens.home.DeleteColor
import com.example.shadeit.Screens.home.DeleteConfirmationDialog
import com.example.shadeit.Screens.home.ExitConfirmationDialog
import com.example.shadeit.Screens.home.bottom.UI.UIPage
import com.example.shadeit.Screens.home.bottom.gradient.GradientColorsScreen
import com.example.shadeit.Screens.home.bottom.gradient.GradientPage
import com.example.shadeit.Screens.home.bottom.solid.SolidPage
import com.example.shadeit.navigation.Screen
import com.example.shadeit.ui.theme.brush
import com.example.shadeit.viewmodel.ColorViewModel
import com.example.shadeit.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import kotlin.text.compareTo
import kotlin.unaryMinus

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class
)
@Composable
fun HomeScreen(navController: NavController, colorViewModel: ColorViewModel, pickerViewModel: PickerViewModel, mainViewModel: MainViewModel, pageState: Int? = null) {

    var selectedNumColors by remember { mutableStateOf<Int?>(null) }
    if(selectedNumColors != null) {
        LaunchedEffect(selectedNumColors) {
            colorViewModel.fetchGradientColors(selectedNumColors)
        }
    }
    var selectedPage by remember { mutableStateOf(0) } // State to track the selected page

    // Trigger fetching UI uploads when on pageState 2
    LaunchedEffect(selectedPage) {
        if (selectedPage == 2) {
            colorViewModel.fetchUIImage() // Fetch all UI uploads
        }
    }

    val context = LocalContext.current
    var showExitDialog by remember { mutableStateOf(false) }
    var backPressedOnce by remember { mutableStateOf(false) }

    // Get the selection mode state
    val isSolidSelectionModeActive by colorViewModel.isSolidSelectionModeActive
    val isGradientSelectionModeActive by colorViewModel.isGradientSelectionModeActive
    val isUiSelectionModeActive by colorViewModel.isUISelectionModeActive
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Get the UI color upload state
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var hasCameraPermission by remember { mutableStateOf(false) }
    var uploadExpanded by remember { mutableStateOf(false) }
    val requestPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        hasCameraPermission = it
    }
    LaunchedEffect(Unit) {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) -> {
                hasCameraPermission = true
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    val pickMediaLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            bitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                ImageDecoder.decodeBitmap(source)
            }
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        bitmap = it
    }

    val rotationAngle by animateFloatAsState(
        targetValue = if (uploadExpanded) 45f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "fabRotation"
    )

    BackHandler {
        if (isSolidSelectionModeActive) {
            colorViewModel.clearSolidSelections()
        } else if (selectedPage == 1) {
            if(selectedNumColors != null) {
                if (isGradientSelectionModeActive) {
                    colorViewModel.clearGradientSelections()
                } else { selectedNumColors = null }
            } else {
                selectedPage = 0
            }
        } else if (selectedPage == 2) {
            if(isUiSelectionModeActive) {
                colorViewModel.clearUISelections()
            } else {
                selectedPage = 0
            }
        } else {
            if (backPressedOnce) {
                showExitDialog = true
            } else {
                backPressedOnce = true
                selectedPage = 0
            }
        }
    }

    if (showExitDialog) {
        ExitConfirmationDialog(
            onConfirm = { (context as? Activity)?.finish() },
            onDismiss = { showExitDialog = false }
        )
    }

    if (showDeleteDialog) {
        DeleteConfirmationDialog(
            onConfirm = {
                if(isSolidSelectionModeActive) {
                    showDeleteDialog = false
                    colorViewModel.deleteSolidColors()
                } else if (isGradientSelectionModeActive) {
                    showDeleteDialog = false
                    colorViewModel.deleteGradientColors(selectedNumColors)
                } else if (isUiSelectionModeActive) {
                    showDeleteDialog = false
                    colorViewModel.deleteUIColors()
                }
            },
            onDismiss = { showDeleteDialog = false }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (selectedPage) {
                            0 -> "Solid Colors"
                            1 -> if(selectedNumColors == null) "Gradient Colors" else "$selectedNumColors Gradient Colors"
                            2 -> if(uploadExpanded) "UI Color Upload" else "Upload UI"
                            3 -> "Color Picker"
                            else -> "ShadeIt"
                        },
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White
                ),
                modifier = Modifier.background(brush)
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier.background(brush),
                contentColor = Color.White,
                containerColor = Color.Transparent
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.Gray) },
                    label = { Text("Home") },
                    selected = selectedPage == 0,
                    onClick = { selectedPage = 0 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.Gray,
                        indicatorColor = Color.Transparent,
                        disabledTextColor = Color.Gray,
                        selectedTextColor = Color.LightGray
                    )
                )
                NavigationBarItem(
                    icon = { Icon(painter = painterResource(id = R.drawable.baseline_palette_24), contentDescription = "Palette", tint = Color.Gray) },
                    label = { Text("Gradient") },
                    selected = selectedPage == 1,
                    onClick = { selectedPage = 1 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.Gray,
                        indicatorColor = Color.Transparent,
                        disabledTextColor = Color.Blue,
                        selectedTextColor = Color.LightGray
                    )
                )
                NavigationBarItem(
                    icon = { Icon(painter = painterResource(id = R.drawable.baseline_upload_file_24), contentDescription = "Upload", tint = Color.Gray) },
                    label = { Text("Upload UI") },
                    selected = selectedPage == 2,
                    onClick = { selectedPage = 2 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.Gray,
                        indicatorColor = Color.Transparent,
                        disabledTextColor = Color.Blue,
                        selectedTextColor = Color.LightGray
                    )
                )
                NavigationBarItem(
                    icon = { Icon(painter = painterResource(R.drawable.baseline_border_color_24), contentDescription = "Color Palette", tint = Color.Gray) },
                    label = { Text("Picker") },
                    selected = selectedPage == 3,
                    onClick = { selectedPage = 3 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.Gray,
                        indicatorColor = Color.Transparent,
                        disabledTextColor = Color.Blue,
                        selectedTextColor = Color.LightGray
                    )
                )
            }
        },
        floatingActionButton = {
            if(selectedPage == 0) {
                if (isSolidSelectionModeActive) {
                    DeleteColor({ showDeleteDialog = true })
                } else {
                    AddColor(onClick = { colorViewModel.addSolidColor() })
                }
            }
            else if (selectedPage == 1) {
                if(selectedNumColors != null) {
                    if (isGradientSelectionModeActive) {
                        DeleteColor({ showDeleteDialog = true })
                    } else {
                        AddColor(onClick = { colorViewModel.addGradientColor(selectedNumColors) })
                    }
                }
            }
            else if (selectedPage == 2) {
                if (isUiSelectionModeActive) {
                    DeleteColor({ showDeleteDialog = true })
                } else {
                    AddColor(onClick = { navController.navigate(Screen.Upload.route) })
                }
            }
        }
    ) {
        AnimatedContent(
            targetState = selectedPage,
            transitionSpec = {
                if (targetState > initialState) {
                    // Left-to-Right Animation
                    slideInHorizontally(initialOffsetX = { it }) + fadeIn() with
                            slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
                } else {
                    // Right-to-Left Animation
                    slideInHorizontally(initialOffsetX = { -it }) + fadeIn() with
                            slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
                }
            },
            modifier = Modifier.padding(it)
        ) { targetPage ->
            when (targetPage) {
                0 -> SolidPage(viewModel = colorViewModel)
                1 -> if(selectedNumColors == null) {
                        GradientPage(selectedNumColors = { selectedNumColors = it })
                    }
                    else {
                        GradientColorsScreen(viewModel = colorViewModel)
                    }
                2 -> UIPage(navController, colorViewModel)
//                3 -> UserProfilePage(navController, mainViewModel)
                3 -> ColorPicker(pickerViewModel)
            }
        }
    }
}