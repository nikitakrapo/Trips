package com.nikitakrapo.trips.components.trip_list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.nikitakrapo.trips.viewmodels.ViewModelFactory

sealed class TripsScreen(val route: String) {
    object UserTripList : TripsScreen("user_trip_list")
}

@OptIn(
    ExperimentalAnimationApi::class,
    ExperimentalMaterialNavigationApi::class
)
fun NavGraphBuilder.tripsScreenGraph(
    navController: NavController,
    viewModelFactory: ViewModelFactory,
    openTripCard: (Trip) -> Unit,
    openAddTrip: () -> Unit,
) {
    composable(
        route = TripsScreen.UserTripList.route,
        enterTransition = { fadeIn(animationSpec = tween(0)) },
        exitTransition = { fadeOut(animationSpec = tween(0)) },
        popEnterTransition = { fadeIn(animationSpec = tween(0)) },
        popExitTransition = { fadeOut(animationSpec = tween(0)) },
    ) {
        val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)

        val userTripListViewModel: UserTripListViewModel =
            ViewModelProvider(viewModelStoreOwner, viewModelFactory)[UserTripListViewModel::class.java]
        val tripsUiState = userTripListViewModel.uiState.collectAsState()
        UserTripList(
            modifier = Modifier
                .fillMaxSize(),
            userTripListUiState = tripsUiState.value,
            onTripCardClick = openTripCard,
            onTripsSwipeRefresh = { userTripListViewModel.onViewEvent(UserTripListEvent.SwipeRefresh) },
            onAddTripClick = openAddTrip,
            onLongTripClick = { userTripListViewModel.onViewEvent(UserTripListEvent.LongTripClick(it)) }
        )
    }
}