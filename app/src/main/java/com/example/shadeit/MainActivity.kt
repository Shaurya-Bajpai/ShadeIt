package com.example.shadeit

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shadeit.api.ApiService
import com.example.shadeit.database.ColorRepository
import com.example.shadeit.navigation.NavigationControl
import com.example.shadeit.session.Manager
import com.example.shadeit.supabase.viewmodel.SupaViewModel
import com.example.shadeit.ui.theme.ShadeItTheme
import com.example.shadeit.viewmodel.ColorViewModel
import com.example.shadeit.viewmodel.ColorViewModelFactory
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {

    private lateinit var sessionManager: Manager // for storing the user session

    var isSigned: Boolean = false

    lateinit var repository: ColorRepository
    lateinit var supaViewmodel: SupaViewModel

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        sessionManager = Manager(this) // Initialize the session manager

        val currentUser = Firebase.auth.currentUser
        if (currentUser != null) {
            isSigned = true
        }

        // Initialize ColorRepository with ApiService
        val apiService = ApiService.create()
        repository = ColorRepository(apiService)
        supaViewmodel = SupaViewModel()

        setContent {
            ShadeItTheme {
                val viewModel: ColorViewModel = viewModel(factory = ColorViewModelFactory(repository, supaViewmodel, applicationContext))
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavigationControl(colorViewModel = viewModel, isSigned = isSigned)
                }
            }
        }
    }
}
