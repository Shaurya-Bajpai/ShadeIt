package com.example.shadeit.navigation

sealed class Screen(val route: String) {

    // Intro & Auth
    object IntroScreen: Screen("intro")
    object AuthScreen: Screen("auth")

    // Authentication
    object SignUpScreen: Screen("signup")
    object LoginScreen: Screen("login")

    object Controller: Screen("controller")

    // Home
    object HomeScreen: Screen("home")

//    object SplashScreen: Screen("splash")

    // Bottom Bar
    object SolidColor: Screen("solid")
    object GradientColor: Screen("gradient")
    object GradientColorScreen: Screen("gradientScreen/{numColors}") {
        fun createRoute(numColors: Int) = "gradientScreen/$numColors"
    }
    object GrokColor: Screen("grok")
    object ProfileScreen: Screen("profile")


    object Upload: Screen("upload")
    object UIPage: Screen("uiPage")
    object UISuggestion: Screen("uiSuggestion")


    object PrivacyPolicy: Screen("privacy")


}