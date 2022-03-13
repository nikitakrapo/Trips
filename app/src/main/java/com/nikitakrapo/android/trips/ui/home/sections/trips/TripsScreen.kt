package com.nikitakrapo.android.trips.ui.home.sections.trips

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.nikitakrapo.android.trips.ui.home.sections.trips.add_trip.AddTrip
import com.nikitakrapo.android.trips.ui.home.sections.trips.add_trip.AddTripEvent
import com.nikitakrapo.android.trips.ui.home.sections.trips.add_trip.AddTripViewModel

sealed class TripsScreen(val route: String) {
    object UserTripList : TripsScreen("user_trip_list")
    object AddTrip : TripsScreen("add_trip")
}

@OptIn(
    ExperimentalAnimationApi::class,
    ExperimentalMaterialNavigationApi::class
)
fun NavGraphBuilder.tripsScreenGraph(navController: NavController) {
    composable(
        route = TripsScreen.UserTripList.route,
        enterTransition = { fadeIn(animationSpec = tween(0)) },
        exitTransition = { fadeOut(animationSpec = tween(0)) },
        popEnterTransition = { fadeIn(animationSpec = tween(0)) },
        popExitTransition = { fadeOut(animationSpec = tween(0)) },
    ) {
        val userTripListViewModel: UserTripListViewModel = viewModel()
        val tripsUiState = userTripListViewModel.uiState.collectAsState()
        UserTripList(
            modifier = Modifier
                .fillMaxSize(),
            userTripListUiState = tripsUiState.value,
            onTripCardClick = {},
            onTripsSwipeRefresh = { userTripListViewModel.onViewEvent(UserTripListEvent.SwipeRefresh) },
            onAddTripClick = { navController.navigate(TripsScreen.AddTrip.route) }
        )
    }

    composable(
        route = TripsScreen.AddTrip.route,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentScope.SlideDirection.Up,
                animationSpec = tween(500)
            )
        },
        exitTransition = { fadeOut(animationSpec = tween(0)) },
        popEnterTransition = { fadeIn(animationSpec = tween(0)) },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentScope.SlideDirection.Down,
                animationSpec = tween(500)
            )
        },
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