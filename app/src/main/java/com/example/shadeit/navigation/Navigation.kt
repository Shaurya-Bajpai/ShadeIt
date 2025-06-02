package com.example.shadeit.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shadeit.Screens.Controller
import com.example.shadeit.Screens.bottom.UI.UIPage
import com.example.shadeit.Screens.bottom.UI.UISuggestion
import com.example.shadeit.Screens.bottom.UI.upload.Upload
import com.example.shadeit.Screens.bottom.colorpicker.PickerViewModel
import com.example.shadeit.Screens.bottom.nav.PrivacyPolicyScreen
import com.example.shadeit.bottom.HomeScreen
import com.example.shadeit.frontend.Screen.AuthScreen
import com.example.shadeit.frontend.Screen.Intro
import com.example.shadeit.frontend.Screen.LoginScreen
import com.example.shadeit.frontend.Screen.SignUpScreen
import com.example.shadeit.viewmodel.ColorViewModel
import com.example.shadeit.viewmodel.MainViewModel

@Composable
fun NavigationControl(
    navController: NavHostController = rememberNavController(),
    mainViewModel: MainViewModel = viewModel(),
    pickerViewModel: PickerViewModel = viewModel(),
    colorViewModel: ColorViewModel,
    isSigned: Boolean
) {

    NavHost(
        navController = navController,
        startDestination = Screen.IntroScreen.route
    ) {

        // Intro & Auth Screens
        composable(Screen.IntroScreen.route){
            Intro(navController = navController, isSigned = isSigned)
        }
        composable(Screen.AuthScreen.route){
            AuthScreen(viewModel = mainViewModel, navController = navController)
        }

        // Authentication Screens
        composable(Screen.SignUpScreen.route){
            SignUpScreen(viewModel = mainViewModel, navController = navController, onRegisterSuccess = { navController.navigate(Screen.Controller.route) })
        }
        composable(Screen.LoginScreen.route){
            LoginScreen(viewModel = mainViewModel, navController = navController, onLoginSuccess = { navController.navigate(Screen.Controller.route) })
        }

        composable(Screen.Controller.route) {
            Controller(navController = navController, colorViewModel = colorViewModel, mainViewModel = mainViewModel, pickerViewModel = pickerViewModel)
        }
        composable(Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }


        composable(Screen.UIPage.route) {
            UIPage(viewModel = colorViewModel, navController = navController)
        }
        composable(Screen.Upload.route) {
            Upload(navController = navController, viewModel = colorViewModel)
        }
        composable(Screen.UISuggestion.route) {
            UISuggestion(navController = navController, viewModel = colorViewModel)
        }


        composable(Screen.PrivacyPolicy.route) {
            PrivacyPolicyScreen(navController = navController)
        }

    }
}