package com.example.shadeit.Screens.home.bottom.UI.upload

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.shadeit.R
import com.example.shadeit.ui.theme.BBlue

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UploadImageScreen() {
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var hasCameraPermission by remember { mutableStateOf(false) }
    var uploadExpanded by remember { mutableStateOf(false) }
    var description by remember { mutableStateOf("") }

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        // Blurred Background
        if (uploadExpanded) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Black.copy(alpha = 0.3f))
                        .clickable { uploadExpanded = false }
                )
            } else {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .clickable { uploadExpanded = false }
                )
            }
        }

        // Display uploaded image
        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .align(Alignment.Center)
                    .clip(RoundedCornerShape(20.dp))
                    .border(1.dp, Color.Blue)
                    .background(Color.Blue)
            )

            OutlinedTextField(
                value = description,
                onValueChange = { des ->
                    description = des
                },
                label = { Text("Write about the project or UI.....") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(size = 10.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, capitalization = KeyboardCapitalization.Words, imeAction = ImeAction.Send)
            )

            Button(
                onClick = {

                },
                modifier = Modifier.fillMaxWidth(),
                enabled = true,
                shape = RoundedCornerShape(size = 12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BBlue)
            ) {
                Text(text = "Search", fontSize = 16.sp, color = Color.White)
            }

        }

        // Animated Camera & Gallery FABs
        AnimatedVisibility(
            visible = uploadExpanded,
            enter = slideInVertically(initialOffsetY = { it/2 }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it/2 }) + fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 30.dp, bottom = 110.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                FloatingActionButton(
                    onClick = {
                        if (hasCameraPermission) {
                            cameraLauncher.launch()
                        } else {
                            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                        uploadExpanded = false
                    },
                    backgroundColor = BBlue,
                    modifier = Modifier.size(50.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_camera_24),
                        contentDescription = "Camera",
                        tint = Color.White
                    )
                }
                Text("Camera", color = Color.White, fontSize = 12.sp)

                Spacer(modifier = Modifier.height(12.dp))

                FloatingActionButton(
                    onClick = {
                        pickMediaLauncher.launch("image/*")
                        uploadExpanded = false
                    },
                    backgroundColor = BBlue,
                    modifier = Modifier.size(50.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_perm_media_24),
                        contentDescription = "Gallery",
                        tint = Color.White
                    )
                }
                Text("Gallery", color = Color.White, fontSize = 12.sp)
            }
        }

        // Main FAB
        FloatingActionButton(
            onClick = { uploadExpanded = !uploadExpanded },
            backgroundColor = BBlue,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Upload Image",
                tint = Color.White,
                modifier = Modifier.rotate(rotationAngle)
            )
        }
    }
}




@Preview(showBackground = true)
@Composable
fun BottomSheetPreview() {
    UploadImageScreen()
}