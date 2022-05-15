package com.nikitakrapo.trips.components.trip_list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.nikitakrapo.impl.ui.TripList
import com.nikitakrapo.impl.viewmodel.UserTripListViewModel
import com.nikitakrapo.trip_list.component.TripList

sealed class TripsScreen(val route: String) {
    object UserTripList : TripsScreen("user_trip_list")
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.tripsScreenGraph(
    navController: NavController,
    openTripCard: (String) -> Unit,
    openAddTrip: () -> Unit,
) {
    composable(
        route = TripsScreen.UserTripList.route,
        enterTransition = { fadeIn(animationSpec = tween(0)) },
        exitTransition = { fadeOut(animationSpec = tween(0)) },
        popEnterTransition = { fadeIn(animationSpec = tween(0)) },
        popExitTransition = { fadeOut(animationSpec = tween(0)) },
    ) {
        val userTripListViewModel: UserTripListViewModel = hiltViewModel()
        LaunchedEffect(Unit) {
            userTripListViewModel.output.collect {
                when (it) {
                    is TripList.Output.AddTripSelected -> openAddTrip()
                    is TripList.Output.Selected -> openTripCard(it.name)
                }
            }
        }
        TripList(
            modifier = Modifier
                .fillMaxSize(),
            component = userTripListViewModel.component,
        )
    }
}