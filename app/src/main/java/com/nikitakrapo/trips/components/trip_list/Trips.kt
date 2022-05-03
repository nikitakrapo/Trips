package com.nikitakrapo.trips.components.trip_list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.nikitakrapo.trips.viewmodels.ViewModelFactory

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Trips(
    modifier: Modifier = Modifier,
    viewModelFactory: ViewModelFactory,
    openTripCard: (Trip) -> Unit,
    openAddTrip: () -> Unit,
) {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navController,
        startDestination = TripsScreen.UserTripList.route,
    ) {
        tripsScreenGraph(
            navController,
            viewModelFactory,
            openTripCard,
            openAddTrip,
        )
    }
}