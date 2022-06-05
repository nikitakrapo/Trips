package com.nikitakrapo.trips.components.application

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nikitakrapo.add_trip.AddTripFeature
import com.nikitakrapo.add_trip.impl.ui.AddTripScreen
import com.nikitakrapo.add_trip.impl.viewmodel.AddTripViewModel
import com.nikitakrapo.login.navigation.AuthorizationDestinations
import com.nikitakrapo.login.navigation.authorizationNavGraph
import com.nikitakrapo.trip_details.TripDetailsFeature
import com.nikitakrapo.trip_details.impl.ui.TripDetailsScreen
import com.nikitakrapo.trip_details.impl.viewmodel.TripDetailsViewModel
import com.nikitakrapo.trips.components.home.Home
import timber.log.Timber

//TODO: move to "root" module
@Composable
fun TripsApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MainDestinations.Home.route
    ) {
        tripsAppNavGraph(navController)
    }
}

//FIXME: get rid of MainDestinations
sealed class MainDestinations(val route: String) {
    object Home : MainDestinations("home")
    object TripDetails : MainDestinations("trip_details") {
        const val tripNameArg = "tripName"
        val fullRoute = "$route/{$tripNameArg}"
    }

    object AddTrip : MainDestinations("add_trip")
}

fun NavGraphBuilder.tripsAppNavGraph(
    navController: NavController,
) {
    composable(MainDestinations.Home.route) {
        Home(
            openLogin = { navController.navigate(AuthorizationDestinations.LogIn.route) },
            openRegistration = { navController.navigate(AuthorizationDestinations.Registration.route) },
            openTripCard = { navController.navigate("${MainDestinations.TripDetails.route}/${it}") },
            openAddTrip = { navController.navigate(MainDestinations.AddTrip.route) },
        )
    }

    authorizationNavGraph(navController)

    composable(
        route = "${MainDestinations.TripDetails.route}/{${MainDestinations.TripDetails.tripNameArg}}",
        arguments = listOf(navArgument(MainDestinations.TripDetails.tripNameArg) {
            type = NavType.StringType
        })
    ) { backStackEntry ->
        val tripName = backStackEntry.arguments?.getString(MainDestinations.TripDetails.tripNameArg)
        if (tripName == null) {
            Timber.e("TripName not passed")
        }
        val tripDetailsViewModel: TripDetailsViewModel = hiltViewModel()
        val uiState = tripDetailsViewModel.component.state.collectAsState()

        LaunchedEffect(Unit) {
            tripDetailsViewModel.component.news.collect { news ->
                when (news) {
                    is TripDetailsFeature.News.CloseScreen ->
                        navController.popBackStack(MainDestinations.TripDetails.fullRoute, true)
                }
            }
        }

        TripDetailsScreen(
            state = uiState.value,
            onBackArrowPressed = {
                navController.popBackStack(MainDestinations.TripDetails.fullRoute, true)
            },
            onMoreClicked = {
                tripDetailsViewModel.component.accept(TripDetailsFeature.Intent.OpenDropdownMenu)
            },
            onMoreDismiss = {
                tripDetailsViewModel.component.accept(TripDetailsFeature.Intent.CloseDropdownMenu)
            },
            onDeleteClicked = {
                tripDetailsViewModel.component.accept(TripDetailsFeature.Intent.DeleteTrip)
            },
        )
    }

    composable(
        route = MainDestinations.AddTrip.route
    ) {
        val addTripViewModel: AddTripViewModel = hiltViewModel()
        val uiState = addTripViewModel.component.state.collectAsState()

        LaunchedEffect(Unit) {
            addTripViewModel.component.news.collect { news ->
                when (news) {
                    AddTripFeature.News.CloseScreen ->
                        navController.popBackStack(MainDestinations.AddTrip.route, true)
                }
            }
        }

        AddTripScreen(
            state = uiState.value,
            onBackArrowPressed = {
                addTripViewModel.component.accept(AddTripFeature.Intent.CloseScreen)
            },
            onNameChanged = { text ->
                addTripViewModel.component.accept(AddTripFeature.Intent.ChangeNameText(text))
            },
            onAddClick = {
                addTripViewModel.component.accept(AddTripFeature.Intent.AddTrip)
            },
        )
    }
}