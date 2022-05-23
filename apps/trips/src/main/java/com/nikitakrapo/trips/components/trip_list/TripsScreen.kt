package com.nikitakrapo.trips.components.trip_list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.nikitakrapo.trip_list.impl.ui.TripList
import com.nikitakrapo.trip_list.impl.viewmodel.UserTripListViewModel
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
        val uiState = userTripListViewModel.component.state.collectAsState()

        LaunchedEffect(Unit) {
            userTripListViewModel.component.news.collect { news ->
                when (news) {
                    is TripList.News.OpenAddTrip -> openAddTrip()
                    is TripList.News.OpenDetails -> openTripCard(news.name)
                }
            }
        }
        TripList(
            modifier = Modifier
                .fillMaxSize(),
            state = uiState.value,
            onAddTripClicked = {
                userTripListViewModel.component.accept(TripList.Intent.OpenAddTrip)
            },
            onSwipeRefresh = {
                userTripListViewModel.component.accept(TripList.Intent.RefreshTrips)
            },
            onTripClicked = { tripName ->
                userTripListViewModel.component.accept(
                    TripList.Intent.OpenTripDetails(tripName)
                )
            },
        )
    }
}