package com.nikitakrapo.add_trip.impl.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nikitakrapo.add_trip.AddTripFeature
import com.nikitakrapo.add_trip.impl.ui.AddTripScreen
import com.nikitakrapo.add_trip.impl.viewmodel.AddTripViewModel
import com.nikitakrapo.navigation.NavigationDestination

sealed class AddTripDestinations(route: String) : NavigationDestination(route) {
    object AddTrip : AddTripDestinations("add_trip")
}

fun NavGraphBuilder.addTripNavGraph(navController: NavController) {
    composable(
        route = AddTripDestinations.AddTrip.route
    ) {
        val addTripViewModel: AddTripViewModel = hiltViewModel()
        val uiState = addTripViewModel.component.state.collectAsState()

        LaunchedEffect(Unit) {
            addTripViewModel.component.news.collect { news ->
                when (news) {
                    AddTripFeature.News.CloseScreen ->
                        navController.popBackStack(AddTripDestinations.AddTrip.route, true)
                }
            }
        }

        AddTripScreen(
            state = uiState.value,
            onBackArrowPressed = {
                addTripViewModel.component.accept(AddTripFeature.Intent.CloseScreen)
            },
            onNameChanged = { text ->
                addTripViewModel.component.accept(AddTripFeature.Intent.ChangeNameText(text))
            },
            onAddClick = {
                addTripViewModel.component.accept(AddTripFeature.Intent.AddTrip)
            },
        )
    }
}