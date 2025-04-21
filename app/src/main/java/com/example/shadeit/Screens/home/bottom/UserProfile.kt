package com.example.shadeit.Screens.home.bottom

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shadeit.navigation.Screen
import com.example.shadeit.session.Manager
import com.example.shadeit.ui.theme.BBlue
import com.example.shadeit.viewmodel.MainViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

@Composable
fun UserProfilePage(navController: NavController, mainViewModel: MainViewModel) {

    var showLogoutDialog by remember { mutableStateOf(false) } // State to manage the dialog

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
//        Text(text = "User Profile", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = mainViewModel.firstName.trim() + " " + mainViewModel.lastName.trim(),
            onValueChange = {},
            shape = RoundedCornerShape(size = 10.dp),
//            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = mainViewModel.email,
            onValueChange = {},
            shape = RoundedCornerShape(size = 10.dp),
//            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = mainViewModel.password,
            onValueChange = {},
            shape = RoundedCornerShape(size = 10.dp),
//            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { /* Navigate to Help */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = BBlue)
        ) {
            Text("Help")
        }

        Button(
            onClick = { /* Navigate to Terms & Conditions */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = BBlue)
        ) {
            Text("Terms & Conditions")
        }

        Button(
            onClick = { /* Navigate to Privacy Policy */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = BBlue)
        ) {
            Text("Privacy Policy")
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                showLogoutDialog = true
            },
            colors = ButtonDefaults.buttonColors(containerColor = BBlue),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout")
        }

        if (showLogoutDialog) {
            LogoutConfirmationDialog(
                onConfirm = {
                    // Handle logout logic here
                    val sessionManager = Manager(navController.context)
                    Firebase.auth.signOut()
                    sessionManager.clearSession()
                    navController.navigate(Screen.AuthScreen.route)
                    showLogoutDialog = false      // Close dialog after logout
                },
                onDismiss = {
                    showLogoutDialog = false      // Close dialog without logging out
                }
            )
        }
    }
}

@Composable
fun LogoutConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Logout") },
        text = { Text(text = "Do you want to logout?") },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text(text = "Yes", color = BBlue)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = "No", color = BBlue)
            }
        },
        titleContentColor = BBlue,
        textContentColor = Color.LightGray,
        containerColor = Color.Black,
        tonalElevation = 20.dp
    )
}

@Preview(showBackground = true)
@Composable
fun UserProfilePreview() {
    UserProfilePage(rememberNavController(), MainViewModel())
    LogoutConfirmationDialog({}, {})
}