package com.example.shadeit.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shadeit.Screens.colorpicker.PickerViewModel
import com.example.shadeit.Screens.home.bottom.UI.UIPage
import com.example.shadeit.Screens.home.bottom.UI.UISuggestion
import com.example.shadeit.Screens.home.bottom.UI.upload.Upload
import com.example.shadeit.bottom.HomeScreen
import com.example.shadeit.frontend.Screen.AuthScreen
import com.example.shadeit.frontend.Screen.Intro
import com.example.shadeit.frontend.Screen.LoginScreen
import com.example.shadeit.frontend.Screen.SignUpScreen
import com.example.shadeit.supabase.viewmodel.SupaViewModel
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
            SignUpScreen(viewModel = mainViewModel, navController = navController)
        }
        composable(Screen.LoginScreen.route){
            LoginScreen(viewModel = mainViewModel, navController = navController)
        }

        // Home Screen
        composable(Screen.HomeScreen.route + "?pageState={pageState}") { backStackEntry ->
            val pageState = backStackEntry.arguments?.getString("pageState")?.toIntOrNull()
            HomeScreen(
                navController = navController,
                colorViewModel = colorViewModel,
                pickerViewModel = pickerViewModel,
                mainViewModel = mainViewModel,
                pageState = pageState
            )
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

//        // Upload UI Screen
//        composable(Screen.Upload.route){
//            Upload(navController, colorViewModel)
//        }

        // Bottom Bar Screens
//        composable(Screen.SolidColor.route){
//            SolidPage(viewModel = colorViewModel)
//        }
//        composable(Screen.GradientColor.route) {
//            GradPage(viewModel = colorViewModel) { numColors ->
//                navController.navigate(Screen.GradientColorScreen.createRoute(numColors))
//            }
//        }
//        composable(Screen.GradientColorScreen.route) { backStackEntry ->
//            val numColors = backStackEntry.arguments?.getString("numColors")?.toInt() ?: 2
//            GradientColorsScreen(viewModel = colorViewModel, numColors = numColors)
//        }
//        composable(Screen.GrokColor.route){
//            SolidPage(viewModel = colorViewModel)
//        }
//        composable(Screen.ProfileScreen.route){
//            ProfileScreen(navController = navController)
//        }

//        composable(Screen.SplashScreen.route){
//            SplashScreen(navController = navController)
//        }
    }
}