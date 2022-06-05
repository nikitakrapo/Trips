package com.nikitakrapo.trips.components.application

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.nikitakrapo.add_trip.impl.navigation.AddTripDestinations
import com.nikitakrapo.add_trip.impl.navigation.addTripNavGraph
import com.nikitakrapo.login.navigation.AuthorizationDestinations
import com.nikitakrapo.login.navigation.authorizationNavGraph
import com.nikitakrapo.trip_details.impl.navigation.TripDetailsDestinations
import com.nikitakrapo.trip_details.impl.navigation.tripDetailsGraph
import com.nikitakrapo.trips.components.home.HomeDestinations
import com.nikitakrapo.trips.components.home.homeNavGraph

//TODO: move to "root" module
@Composable
fun TripsApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HomeDestinations.Home.route
    ) {
        tripsAppNavGraph(navController)
    }
}

fun NavGraphBuilder.tripsAppNavGraph(
    navController: NavController,
) {
    homeNavGraph(
        navController,
        openLogin = { navController.navigate(AuthorizationDestinations.LogIn.route) },
        openRegistration = { navController.navigate(AuthorizationDestinations.Registration.route) },
        openTripCard = { navController.navigate(TripDetailsDestinations.TripDetails.getRouteForTrip(it)) },
        openAddTrip = { navController.navigate(AddTripDestinations.AddTrip.route) },
    )

    authorizationNavGraph(navController)

    tripDetailsGraph(navController)

    addTripNavGraph(navController)
}