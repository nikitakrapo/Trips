package com.nikitakrapo.trips.components.home

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nikitakrapo.navigation.NavigationDestination
import com.nikitakrapo.trips.R

sealed class HomeDestinations(route: String) : NavigationDestination(route) {
    object Home : HomeDestinations("home")
}

sealed class HomeSections(val route: String, @StringRes val titleResourceId: Int, val imageVector: ImageVector) {
    object Trips : HomeSections("trips", R.string.trips_screen_title, Icons.Filled.Place)
    object Profile : HomeSections("profile", R.string.profile_screen_title, Icons.Filled.Person)
}

fun NavGraphBuilder.homeNavGraph(
    navController: NavController,
    openLogin: () -> Unit,
    openRegistration: () -> Unit,
    openTripCard: (String) -> Unit,
    openAddTrip: () -> Unit,
) {
    composable(HomeDestinations.Home.route) {
        Home(
            openLogin = openLogin,
            openRegistration = openRegistration,
            openTripCard = openTripCard,
            openAddTrip = openAddTrip,
        )
    }
}