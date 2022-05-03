package com.nikitakrapo.trips.components.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.nikitakrapo.trips.components.profile.Profile
import com.nikitakrapo.trips.components.trip_list.Trip
import com.nikitakrapo.trips.components.trip_list.Trips
import com.nikitakrapo.trips.viewmodels.ViewModelFactory

@OptIn(
    ExperimentalAnimationApi::class,
    com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi::class,
    androidx.compose.material.ExperimentalMaterialApi::class
)
@Composable
fun Home(
    openLogin: () -> Unit,
    viewModelFactory: ViewModelFactory,
    openTripCard: (Trip) -> Unit,
    openAddTrip: () -> Unit,
) {
    val screens = listOf(
        HomeSections.Trips,
        HomeSections.Profile
    )
    val navController = rememberAnimatedNavController()
    Scaffold(
        bottomBar = {
            TripsNavigationBar(
                navController = navController,
                homeSections = screens
            )
        }
    ) { innerPadding ->
        AnimatedNavHost(
            navController,
            startDestination = HomeSections.Trips.route,
            Modifier.padding(innerPadding)
        ) {
            screens.forEach { screen ->
                when (screen) {

                    HomeSections.Trips -> composable(
                        route = screen.route,
                        enterTransition = { fadeIn(animationSpec = tween(0)) },
                        exitTransition = { fadeOut(animationSpec = tween(0)) },
                        popEnterTransition = { fadeIn(animationSpec = tween(0)) },
                        popExitTransition = { fadeOut(animationSpec = tween(0)) },
                    ) {
                        Trips(
                            viewModelFactory = viewModelFactory,
                            openAddTrip = openAddTrip,
                            openTripCard = openTripCard,
                        )
                    }

                    HomeSections.Profile -> composable(
                        route = screen.route,
                        enterTransition = { fadeIn(animationSpec = tween(0)) },
                        exitTransition = { fadeOut(animationSpec = tween(0)) },
                        popEnterTransition = { fadeIn(animationSpec = tween(0)) },
                        popExitTransition = { fadeOut(animationSpec = tween(0)) },
                    ) {
                        Profile(
                            navigateToLogin = openLogin
                        )
                    }
                }
            }
        }
    }
}
