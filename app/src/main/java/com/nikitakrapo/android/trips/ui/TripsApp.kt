package com.nikitakrapo.android.trips.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.nikitakrapo.android.trips.ui.navigation.Screen
import com.nikitakrapo.android.trips.ui.navigation.tripsAppNavGraph

@Composable
fun TripsApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        tripsAppNavGraph(navController)
    }
}