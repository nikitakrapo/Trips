package com.nikitakrapo.trips.components.trip_list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.nikitakrapo.trips.data.dto.Trip

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Trips(
    modifier: Modifier = Modifier,
    openTripCard: (Trip) -> Unit,
    openAddTrip: () -> Unit,
) {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = TripsScreen.UserTripList.route,
    ) {
        tripsScreenGraph(
            navController,
            openTripCard,
            openAddTrip,
        )
    }
}