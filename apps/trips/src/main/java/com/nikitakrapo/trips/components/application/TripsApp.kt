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
import com.nikitakrapo.trip_details.TripDetailsFeature
import com.nikitakrapo.trip_details.impl.ui.TripDetailsScreen
import com.nikitakrapo.trip_details.impl.viewmodel.TripDetailsViewModel
import com.nikitakrapo.trips.components.home.Home
import com.nikitakrapo.trips.components.login.loginGraph
import timber.log.Timber

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
    object TripDetails : MainSections("trip_details") {
        const val tripNameArg = "tripName"
        val fullRoute = "$route/{$tripNameArg}"
    }

    object AddTrip : MainSections("add_trip")
}

fun NavGraphBuilder.tripsAppNavGraph(
    navController: NavController,
) {
    composable(MainSections.Home.route) {
        Home(
            openLogin = { navController.navigate(MainSections.Login.route) },
            openTripCard = { navController.navigate("${MainSections.TripDetails.route}/${it}") }, // xd
            openAddTrip = { navController.navigate(MainSections.AddTrip.route) },
        )
    }

    loginGraph(navController)

    composable(
        route = "${MainSections.TripDetails.route}/{${MainSections.TripDetails.tripNameArg}}",
        arguments = listOf(navArgument(MainSections.TripDetails.tripNameArg) {
            type = NavType.StringType
        })
    ) { backStackEntry ->
        val tripName = backStackEntry.arguments?.getString(MainSections.TripDetails.tripNameArg)
        if (tripName == null) {
            Timber.e("TripName not passed")
        }
        val tripDetailsViewModel: TripDetailsViewModel = hiltViewModel()
        val uiState = tripDetailsViewModel.component.state.collectAsState()

        LaunchedEffect(Unit) {
            tripDetailsViewModel.component.news.collect { news ->
                when (news) {
                    is TripDetailsFeature.News.CloseScreen ->
                        navController.popBackStack(MainSections.TripDetails.fullRoute, true)
                }
            }
        }

        TripDetailsScreen(
            state = uiState.value,
            onBackArrowClicked = {
                navController.popBackStack(MainSections.TripDetails.fullRoute, true)
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
        route = MainSections.AddTrip.route
    ) {
        val addTripViewModel: AddTripViewModel = hiltViewModel()
        val uiState = addTripViewModel.component.state.collectAsState()

        LaunchedEffect(Unit) {
            addTripViewModel.component.news.collect { news ->
                when (news) {
                    AddTripFeature.News.CloseScreen ->
                        navController.popBackStack(MainSections.AddTrip.route, true)
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