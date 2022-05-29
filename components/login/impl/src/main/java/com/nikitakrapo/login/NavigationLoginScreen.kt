package com.nikitakrapo.login

import com.nikitakrapo.login.MainLoginFeature.LoginScreen

sealed class NavigationLoginScreen(
    val tripScreen: LoginScreen,
    val route: String,
) {
    object LogIn : NavigationLoginScreen(LoginScreen.LogIn, "log_in")
    object Register : NavigationLoginScreen(LoginScreen.Register, "register")
}