package com.nikitakrapo.trips.components.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.nikitakrapo.trips.components.application.MainSections

sealed class LoginScreen(val route: String) {
    object SignIn : LoginScreen("sign_in")
    object Registration : LoginScreen("registration")
}

fun NavGraphBuilder.loginGraph(navController: NavController) {
    navigation(
        startDestination = LoginScreen.SignIn.route,
        route = MainSections.Login.route
    ) {
        composable(route = LoginScreen.SignIn.route) {
            SignIn(
                navigateToRegistration = { navController.navigate(LoginScreen.Registration.route) },
                goBack = { navController.popBackStack(LoginScreen.SignIn.route, true) }
            )
        }

        composable(route = LoginScreen.Registration.route) { Registration() }
    }
}