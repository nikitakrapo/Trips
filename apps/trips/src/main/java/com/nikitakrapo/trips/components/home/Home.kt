package com.nikitakrapo.trips.components.home

import androidx.compose.animation.ExperimentalAnimationApi
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
import com.nikitakrapo.profile.ProfileFeature
import com.nikitakrapo.profile.ui.ProfileScreen
import com.nikitakrapo.profile.viewmodel.ProfileViewModel
import com.nikitakrapo.trip_list.component.TripListFeature
import com.nikitakrapo.trip_list.impl.ui.TripList
import com.nikitakrapo.trip_list.impl.viewmodel.UserTripListViewModel

//TODO: move to "home" module
@OptIn(
    ExperimentalAnimationApi::class,
    com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi::class,
    androidx.compose.material.ExperimentalMaterialApi::class
)
@Composable
fun Home(
    openAuthorization: () -> Unit,
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
                    HomeSections.Trips -> composable(
                        route = screen.route,
                    ) {
                        val userTripListViewModel: UserTripListViewModel = hiltViewModel()
                        val uiState = userTripListViewModel.component.state.collectAsState()

                        LaunchedEffect(Unit) {
                            userTripListViewModel.component.news.collect { news ->
                                when (news) {
                                    is TripListFeature.News.OpenAddTrip -> openAddTrip()
                                    is TripListFeature.News.OpenDetails -> openTripCard(news.name)
                                }
                            }
                        }
                        TripList(
                            modifier = Modifier
                                .fillMaxSize(),
                            state = uiState.value,
                            onAddTripClicked = {
                                userTripListViewModel.component.accept(TripListFeature.Intent.OpenAddTrip)
                            },
                            onSwipeRefresh = {
                                userTripListViewModel.component.accept(TripListFeature.Intent.RefreshTrips)
                            },
                            onTripClicked = { tripName ->
                                userTripListViewModel.component.accept(
                                    TripListFeature.Intent.OpenTripDetails(tripName)
                                )
                            },
                        )
                    }

                    HomeSections.Profile -> composable(
                        route = screen.route,
                    ) {
                        val profileViewModel: ProfileViewModel = hiltViewModel()
                        val uiState = profileViewModel.component.state.collectAsState()
                        val component = profileViewModel.component

                        LaunchedEffect(Unit) {
                            profileViewModel.component.news.collect { news ->
                                when (news) {
                                    is ProfileFeature.News.OpenAuthorization -> openAuthorization()
                                }
                            }
                        }

                        ProfileScreen(
                            state = uiState.value,
                            openAuthorization = { component.accept(ProfileFeature.Intent.OpenAuthorization) },
                        )
                    }
                }
            }
        }
    }
}
