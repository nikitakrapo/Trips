package com.nikitakrapo.trips.components.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nikitakrapo.profile.ProfileFeature
import com.nikitakrapo.profile.ui.ProfileScreen
import com.nikitakrapo.profile.viewmodel.ProfileViewModel
import com.nikitakrapo.trip_list.component.TripListFeature
import com.nikitakrapo.trip_list.impl.ui.TripList
import com.nikitakrapo.trip_list.impl.viewmodel.UserTripListViewModel

//TODO: move to "home" module
@Composable
fun Home(
    openLogin: () -> Unit,
    openRegistration: () -> Unit,
    openTripCard: (String) -> Unit,
    openAddTrip: () -> Unit,
) {
    val screens = listOf(
        HomeSections.Trips,
        HomeSections.Profile
    )
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            TripsNavigationBar(
                navController = navController,
                homeSections = screens
            )
        }
    ) { innerPadding ->
        NavHost(
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
                                    is ProfileFeature.News.OpenLogin -> openLogin()
                                    is ProfileFeature.News.OpenRegistration -> openRegistration()
                                }
                            }
                        }

                        ProfileScreen(
                            state = uiState.value,
                            openLogin = { component.accept(ProfileFeature.Intent.OpenLogin) },
                            openRegistration = { component.accept(ProfileFeature.Intent.OpenRegistration) },
                            signOut = { component.accept(ProfileFeature.Intent.SignOut) },
                            openSettings = { /*TODO*/ },
                            deleteAccount = { component.accept(ProfileFeature.Intent.RemoveAccount) }
                        )
                    }
                }
            }
        }
    }
}
