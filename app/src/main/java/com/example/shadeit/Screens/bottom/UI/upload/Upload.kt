package com.example.shadeit.Screens.bottom.UI.upload

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.shadeit.navigation.Screen
import com.example.shadeit.ui.theme.BBlue
import com.example.shadeit.ui.theme.brush
import com.example.shadeit.viewmodel.ColorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Upload(navController: NavController, viewModel: ColorViewModel) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var hasCameraPermission by remember { mutableStateOf(false) }
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

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
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
                            .clickable(onClick = { navController.navigateUp() })
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
                .background(Color.Black)
                .padding(vertical = 12.dp, horizontal = 16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(it)
                    .size(350.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
                    .clickable(onClick = { launcher.launch("image/*") }),
            ) {
                if (imageUri != null) {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = "random image",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(300.dp)
                    )
                } else {
                    Text("Upload your UI", color = Color.Gray)
                }
            }

            Spacer(Modifier.height(32.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { des ->
                    description = des
                },
                placeholder = { Text("Write about the project or UI...", color = Color.Gray) },
                modifier = Modifier.padding(horizontal = 6.dp).fillMaxWidth(),
                shape = RoundedCornerShape(size = 10.dp),
                maxLines = 7,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedIndicatorColor = Color.Gray,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, capitalization = KeyboardCapitalization.Sentences, imeAction = ImeAction.Send)
            )

            Spacer(Modifier.height(32.dp))

            Column(
                modifier = Modifier.padding(bottom = 20.dp).fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Button(
                    onClick = {
                        if (imageUri != null) {
                            val (imageValid, message) = validateImage(context, imageUri!!)
                            if(!imageValid) {
                                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                            }
                            else if (description.isNotEmpty()) {
                                // Upload the image and description
                                viewModel.selectedUIUpload = null
                                viewModel.suggestColors(imageUri!!, description)
                                navController.navigate(Screen.UISuggestion.route)

//                                description = "" // Clear the description after submission

                            } else {
                                Toast.makeText(context, "Enter a description", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "Upload an UI", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.padding(horizontal = 8.dp).fillMaxWidth(),
                    enabled = true,
                    shape = RoundedCornerShape(size = 12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BBlue)
                ) {
                    Text(
                        text = "Suggest Colors",
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }

    }
}
