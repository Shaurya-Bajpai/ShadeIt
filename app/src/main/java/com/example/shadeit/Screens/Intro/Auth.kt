package com.example.shadeit.frontend.Screen

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shadeit.navigation.Screen
import com.example.shadeit.ui.theme.BBlue
import com.example.shadeit.ui.theme.brush
import com.example.shadeit.viewmodel.MainViewModel
import com.example.shadeit.R
import com.example.shadeit.Secrets
import com.example.shadeit.ui.theme.back3
import com.example.shadeit.ui.theme.brush2
import com.example.shadeit.ui.theme.brush3
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun AuthScreen(viewModel: MainViewModel, navController: NavController) {

    val context = LocalContext.current

    // Sign in with Google
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(Secrets.WEB_CLIENT_ID)  // Replace with your web client ID
        .requestEmail()
        .build()
    val googleSignInClient = GoogleSignIn.getClient(context, gso)
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener { authResult ->
                    if (authResult.isSuccessful) {
                        val user = FirebaseAuth.getInstance().currentUser
                        viewModel.firstName = user?.displayName ?: ""
                        viewModel.email = user?.email ?: ""
                        Toast.makeText(context, "SignIn successful", Toast.LENGTH_SHORT).show()
                        navController.navigate(Screen.HomeScreen.route)
                    } else {
                        Toast.makeText(context, "SignIn failed", Toast.LENGTH_SHORT).show()
                    }
                }
        } catch (e: ApiException) {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            Log.e("GoogleSignIn", "Sign-in failed", e)
        }
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .background(brush)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome !",
                fontSize = 32.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )

            Spacer(modifier = Modifier.height(50.dp))

            // Buttons for Sign Up and Login
            Button(
                onClick = {
                    navController.navigate(Screen.SignUpScreen.route)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = BBlue,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Create Account",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = Color.White,
                    modifier = Modifier.padding(5.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = {
                    navController.navigate(Screen.LoginScreen.route)
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.Blue,
                    backgroundColor = Color.Transparent
                ),
                border = BorderStroke(2.dp, brush),
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Login",
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(5.dp)
                )
            }

            Row(
                modifier = Modifier
                    .padding(vertical = 32.dp, horizontal = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.weight(1f))
                Text(
                    text = "or",
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
                Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.weight(1f))

            }

//             Social media icons (placeholders)
            Row(
                modifier = Modifier.padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    googleSignInClient.signOut().addOnCompleteListener {
                        val signInIntent = googleSignInClient.signInIntent
                        launcher.launch(signInIntent)
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.google), // Continue with Google
                        contentDescription = "Google",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenPreview(){
    AuthScreen(MainViewModel(), rememberNavController())
}