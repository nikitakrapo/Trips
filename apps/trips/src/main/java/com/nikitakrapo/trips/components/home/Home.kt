package com.nikitakrapo.trips.components.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.nikitakrapo.trip_list.component.TripList
import com.nikitakrapo.trip_list.impl.ui.TripList
import com.nikitakrapo.trip_list.impl.viewmodel.UserTripListViewModel
import com.nikitakrapo.trips.components.profile.Profile

//TODO: move to "home" module
@OptIn(
    ExperimentalAnimationApi::class,
    com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi::class,
    androidx.compose.material.ExperimentalMaterialApi::class
)
@Composable
fun Home(
    openLogin: () -> Unit,
    openTripCard: (String) -> Unit,
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

                    HomeSections.Trips ->     composable(
                        route = screen.route,
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
