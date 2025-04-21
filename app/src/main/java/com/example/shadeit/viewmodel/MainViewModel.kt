package com.example.shadeit.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.shadeit.navigation.NavigationControl
import com.example.shadeit.session.Manager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser

class MainViewModel: ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // SignUp Variables
    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    // SignIn Variables
    var signin_email by mutableStateOf("")
    var signin_password by mutableStateOf("")

    // Firebase Authentication
    fun signInUser(context: Context, onResult: (FirebaseUser?, String?) -> Unit) {
        auth.signInWithEmailAndPassword(signin_email, signin_password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.getIdToken(true)?.addOnCompleteListener { tokenTask ->
                        if (tokenTask.isSuccessful) {
                            val token = tokenTask.result?.token
                            if (token != null) {
                                // Save user session
                                val sessionManager = Manager(context)
                                sessionManager.saveUserSession(user.uid, token)
                                onResult(user, null)
                            } else {
                                onResult(null, "Failed to get token")
                            }
                        } else {
                            onResult(null, tokenTask.exception?.message)
                        }
                    }
                } else {
                    onResult(null, task.exception?.message)
                }
            }
    }

    fun signUpUser(context: Context, onResult: (FirebaseUser?, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.getIdToken(true)?.addOnCompleteListener { tokenTask ->
                        if (tokenTask.isSuccessful) {
                            val token = tokenTask.result?.token
                            if (token != null) {
                                // Save user session
                                val sessionManager = Manager(context)
                                sessionManager.saveUserSession(user.uid, token)
                                onResult(user, null)
                            } else {
                                onResult(null, "Failed to get token")
                            }
                        } else {
                            onResult(null, tokenTask.exception?.message)
                        }
                    }
                } else {
                    onResult(null, task.exception?.message)
                }
            }
    }
}