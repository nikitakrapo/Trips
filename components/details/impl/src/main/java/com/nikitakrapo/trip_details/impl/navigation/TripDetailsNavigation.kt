package com.nikitakrapo.trip_details.impl.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nikitakrapo.navigation.NavigationDestination
import com.nikitakrapo.trip_details.TripDetailsFeature
import com.nikitakrapo.trip_details.impl.ui.TripDetailsScreen
import com.nikitakrapo.trip_details.impl.viewmodel.TripDetailsViewModel

sealed class TripDetailsDestinations(route: String) : NavigationDestination(route) {
    object TripDetails : TripDetailsDestinations("trip_details") {
        const val tripNameArg = "tripName"
        val fullRoute = "$route/{$tripNameArg}"
        fun getRouteForTrip(tripName: String): String =
            "${route}/${tripName}"
    }
}

fun NavGraphBuilder.tripDetailsGraph(navController: NavController) {
    composable(
        route = "${TripDetailsDestinations.TripDetails.route}/{${TripDetailsDestinations.TripDetails.tripNameArg}}",
        arguments = listOf(navArgument(TripDetailsDestinations.TripDetails.tripNameArg) {
            type = NavType.StringType
        })
    ) {
        val tripDetailsViewModel: TripDetailsViewModel = hiltViewModel()
        val uiState = tripDetailsViewModel.component.state.collectAsState()

        LaunchedEffect(Unit) {
            tripDetailsViewModel.component.news.collect { news ->
                when (news) {
                    is TripDetailsFeature.News.CloseScreen ->
                        navController.popBackStack(TripDetailsDestinations.TripDetails.fullRoute, true)
                }
            }
        }

        TripDetailsScreen(
            state = uiState.value,
            onBackArrowPressed = {
                navController.popBackStack(TripDetailsDestinations.TripDetails.fullRoute, true)
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
}
