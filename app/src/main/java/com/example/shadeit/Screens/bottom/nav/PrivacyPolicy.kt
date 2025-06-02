package com.example.shadeit.Screens.bottom.nav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shadeit.navigation.Screen
import com.example.shadeit.ui.theme.brush

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Privacy Policy",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                    )
                },
                navigationIcon = { navController.navigate(Screen.Controller.route) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White
                ),
                modifier = Modifier.background(brush)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Privacy Policy for ShadeIt", color = Color.White, fontSize = 16.sp)
            Spacer(Modifier.height(8.dp))
            Text("At ShadeIt, we take your privacy seriously. This Privacy Policy explains how we collect, use, and protect your information when you use our mobile application.", color = Color.White, fontSize = 14.sp)
            Spacer(Modifier.height(8.dp))

            Text(
                text = "1. Information We Collect\n" +
                        "- Authentication data (email) via Firebase.\n" +
                        "- Uploaded images and associated metadata (color tags, descriptions).\n" +
                        "- User feedback (if submitted).",
                color = Color.White,
                fontSize = 12.sp
            )
            Spacer(Modifier.height(8.dp))

            Text(
                text = "2. How We Use Information\n" +
                        "- To authenticate users.\n" +
                        "- To store and manage images and color data in Firestore.\n" +
                        "- To analyze and improve user experience based on feedback.",
                color = Color.White,
                fontSize = 12.sp
            )
            Spacer(Modifier.height(8.dp))

            Text(
                text = "3. Data Storage\n" +
                        "- All data is securely stored in Firebase Firestore and Firebase Storage. \n" +
                        "- We do not share your data with third-party services except Firebase.\n",
                color = Color.White,
                fontSize = 12.sp
            )
            Spacer(Modifier.height(8.dp))

            Text(
                text = "4. User Rights\n" +
                        "- You may request to delete your account and associated data at any time.\n" +
                        "- We do not collect any sensitive or financial data.\n",
                color = Color.White,
                fontSize = 12.sp
            )
            Spacer(Modifier.height(8.dp))

            Text(
                text = "5. Updates\n" +
                        "We may update this Privacy Policy periodically. Continued use of the app implies acceptance of the updated policy.\n",
                color = Color.White,
                fontSize = 12.sp
            )
            Spacer(Modifier.height(8.dp))

            Text(
                text = "If you have any questions, contact us via the in-app form.",
                color = Color.White,
                fontSize = 12.sp
            )
            Spacer(Modifier.height(8.dp))

        }
    }
}