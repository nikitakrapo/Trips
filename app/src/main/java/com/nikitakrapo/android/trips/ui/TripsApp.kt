package com.nikitakrapo.android.trips.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nikitakrapo.android.trips.ui.add_trip.AddTrip
import com.nikitakrapo.android.trips.ui.add_trip.AddTripEvent
import com.nikitakrapo.android.trips.ui.add_trip.AddTripViewModel
import com.nikitakrapo.android.trips.ui.home.Home
import com.nikitakrapo.android.trips.ui.home.trips.Trip
import com.nikitakrapo.android.trips.ui.login.loginGraph
import com.nikitakrapo.android.trips.ui.trip.TripDetail

@Composable
fun TripsApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MainSections.Home.route
    ) {
        tripsAppNavGraph(navController)
    }
}

sealed class MainSections(val route: String) {
    object Home : MainSections("home")
    object Login : MainSections("login")
    object TripDetails : MainSections("trip_details")
    object AddTrip : MainSections("add_trip")
}

fun NavGraphBuilder.tripsAppNavGraph(navController: NavController) {
    composable(MainSections.Home.route) {
        Home(
            openLogin = { navController.navigate(MainSections.Login.route) },
            openTripCard = { navController.navigate(MainSections.TripDetails.route) },
            openAddTrip = { navController.navigate(MainSections.AddTrip.route) },
        )
    }

    loginGraph(navController)
    
    composable(
        route = MainSections.TripDetails.route
    ) {
        TripDetail(trip = Trip())
    }

    composable(
        route = MainSections.AddTrip.route
    ) {
        val addTripViewModel: AddTripViewModel = viewModel()
        val addTripUiState = addTripViewModel.uiState.collectAsState()
        AddTrip(
            uiState = addTripUiState.value,
            onNameChanged = { newName ->
                addTripViewModel.onViewEvent(AddTripEvent.NameTextFieldChanged(newName))
            },
            onBackArrow = { navController.popBackStack() }
        )
    }
}