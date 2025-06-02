package com.example.shadeit.frontend.Screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.input.KeyboardCapitalization
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
fun SignUpScreen(
    navController: NavController,
    viewModel: MainViewModel,
    onRegisterSuccess: () -> Unit
) {
    val context = LocalContext.current

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
                    text = "Register",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(start = 24.dp, bottom = 8.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    Text(
                        text = "Already have account? ",
                        modifier = Modifier.padding(start = 24.dp),
                        color = Color.White
                    )
                    Text(
                        text = "Sign In",
                        color = Color.White,
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.LoginScreen.route) {
                                popUpTo(Screen.AuthScreen.route)
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(72.dp))
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(8.dp)
            ) {

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = viewModel.firstName,
                            onValueChange = { viewModel.firstName = it },
                            label = { Text("First Name") },
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            singleLine = true,
                            shape = RoundedCornerShape(size = 10.dp),
                            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences, keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
                        )

                        OutlinedTextField(
                            value = viewModel.lastName,
                            onValueChange = { viewModel.lastName = it },
                            label = { Text("Last Name") },
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            singleLine = true,
                            shape = RoundedCornerShape(size = 10.dp),
                            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences, keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = viewModel.email,
                        onValueChange = { viewModel.email = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(size = 10.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = viewModel.password,
                        onValueChange = { viewModel.password = it },
                        label = { Text("Create Password") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(size = 10.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Send),
                        visualTransformation = PasswordVisualTransformation()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if(viewModel.firstName.isEmpty() || viewModel.email.isEmpty() || viewModel.password.isEmpty()) {
                                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_LONG).show()
                            } else {
                                viewModel.signUpUser(context = context) { user, error ->
                                    if (user != null) {
                                        Toast.makeText(context, "Registration Successful", Toast.LENGTH_LONG).show()
                                        onRegisterSuccess()
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
                        Text(text = "Register", fontSize = 16.sp, color = Color.White)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    SignUpScreen(rememberNavController(), MainViewModel(), {})
}