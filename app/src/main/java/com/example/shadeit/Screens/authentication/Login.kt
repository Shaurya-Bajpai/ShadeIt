package com.example.shadeit.frontend.Screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shadeit.Screens.common.Topbar
import com.example.shadeit.navigation.Screen
import com.example.shadeit.ui.theme.BBlue
import com.example.shadeit.ui.theme.brush
import com.example.shadeit.viewmodel.MainViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: MainViewModel,
    onLoginSuccess: () -> Unit
) {

    val context = LocalContext.current
    var showExitDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { Topbar(navController) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(brush)
                    .padding(it)
            ) {

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Sign in to your\nAccount",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(start = 24.dp, bottom = 8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    Text(
                        text = "Don't have account? ",
                        modifier = Modifier.padding(start = 24.dp),
                        color = Color.White
                    )
                    Text(
                        text = "Sign Up",
                        color = Color.White,
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.SignUpScreen.route) {
                                popUpTo(Screen.AuthScreen.route)
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(48.dp))

            }

            Column(
                modifier = Modifier
                    .background(Color.Black)
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = viewModel.signin_email,
                    onValueChange = { viewModel.signin_email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(size = 10.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = viewModel.signin_password,
                    onValueChange = { viewModel.signin_password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(size = 10.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Send),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = {
                        if(viewModel.signin_email.isEmpty() || viewModel.signin_password.isEmpty()) {
                            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_LONG).show()
                        } else {
                            viewModel.signInUser(context = context) { user, error ->
                                if (user != null) {
                                    Toast.makeText(context, "Login Successful", Toast.LENGTH_LONG).show()
                                    onLoginSuccess()
                                } else {
                                    Toast.makeText(context, error ?: "Something Went Wrong", Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = true,
                    shape = RoundedCornerShape(size = 10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BBlue)
                ) {
                    Text(text = "Login", fontSize = 16.sp, color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(rememberNavController(), MainViewModel(), {})
}