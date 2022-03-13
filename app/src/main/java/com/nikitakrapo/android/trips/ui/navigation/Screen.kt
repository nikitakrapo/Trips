package com.nikitakrapo.android.trips.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nikitakrapo.android.trips.ui.home.Home
import com.nikitakrapo.android.trips.ui.login.loginGraph

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Login : Screen("login")
}

fun NavGraphBuilder.tripsAppNavGraph(navController: NavController) {
    composable(Screen.Home.route) {
        Home(navigateToLogin = { navController.navigate(Screen.Login.route) })
    }

    loginGraph(navController)
}