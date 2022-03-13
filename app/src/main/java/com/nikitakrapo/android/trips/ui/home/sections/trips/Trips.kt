package com.nikitakrapo.android.trips.ui.home.sections.trips

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Trips(
    modifier: Modifier = Modifier,
) {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navController,
        startDestination = TripsScreen.UserTripList.route,
    ) {
        tripsScreenGraph(navController)
    }
}