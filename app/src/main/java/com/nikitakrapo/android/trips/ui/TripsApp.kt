package com.nikitakrapo.android.trips.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.arkivanov.mvikotlin.timetravel.store.TimeTravelStoreFactory
import com.nikitakrapo.android.trips.appComponent
import com.nikitakrapo.android.trips.ui.MainSections.*
import com.nikitakrapo.android.trips.ui.add_trip.AddTrip
import com.nikitakrapo.android.trips.ui.add_trip.AddTripAction
import com.nikitakrapo.android.trips.ui.add_trip.AddTripEvent
import com.nikitakrapo.android.trips.ui.add_trip.AddTripViewModel
import com.nikitakrapo.android.trips.ui.home.Home
import com.nikitakrapo.android.trips.ui.login.loginGraph
import com.nikitakrapo.android.trips.ui.trip.TripDetail
import com.nikitakrapo.android.trips.ui.trip.TripDetailComponent
import com.nikitakrapo.android.trips.viewmodels.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

@Composable
fun TripsApp(
    viewModelFactory: ViewModelFactory
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Home.route
    ) {
        tripsAppNavGraph(navController, viewModelFactory)
    }
}

sealed class MainSections(val route: String) {
    object Home : MainSections("home")
    object Login : MainSections("login")
    object TripDetails : MainSections("trip_details") {
        const val tripNameArg = "tripName"
    }

    object AddTrip : MainSections("add_trip")
}

fun NavGraphBuilder.tripsAppNavGraph(
    navController: NavController,
    viewModelFactory: ViewModelFactory
) {
    composable(Home.route) {
        Home(
            viewModelFactory = viewModelFactory,
            openLogin = { navController.navigate(Login.route) },
            openTripCard = { navController.navigate("${TripDetails.route}/${it.name}") }, // xd
            openAddTrip = { navController.navigate(AddTrip.route) },
        )
    }

    loginGraph(navController)

    composable(
        route = "${TripDetails.route}/{${TripDetails.tripNameArg}}",
        arguments = listOf(navArgument(TripDetails.tripNameArg) {
            type = NavType.StringType
        })
    ) { backStackEntry ->
        val tripName = backStackEntry.arguments?.getString(TripDetails.tripNameArg)
        if (tripName == null) {
            Timber.e("TripName not passed")
        }
        val tripsRepository = LocalContext.current.appComponent.tripsRespository() //TODO: DI
        val component = TripDetailComponent(
            storeFactory = TimeTravelStoreFactory(),
            componentContext = Dispatchers.Main,
            tripsRepository = tripsRepository,
            tripName = tripName ?: "" //TODO: resolve this normally
        )
        TripDetail(tripDetail = component, closeScreen = { navController.popBackStack() })
    }

    composable(
        route = AddTrip.route
    ) {
        val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)

        val addTripViewModel: AddTripViewModel =
            ViewModelProvider(viewModelStoreOwner, viewModelFactory)[AddTripViewModel::class.java]
        val addTripUiState = addTripViewModel.uiState.collectAsState()
        val addTripActions = addTripViewModel.actions.collectAsState()
        val event = addTripActions.value

        LaunchedEffect(event) {
            when (event) {
                AddTripAction.CloseScreen -> {
                    navController.popBackStack()
                }
                null -> {}
            }
        }

        AddTrip(
            uiState = addTripUiState.value,
            onNameChanged = { newName ->
                addTripViewModel.onViewEvent(AddTripEvent.NameTextFieldChanged(newName))
            },
            onBackArrow = { addTripViewModel.onViewEvent(AddTripEvent.BackArrowClicked) },
            onAddClick = { addTripViewModel.onViewEvent(AddTripEvent.AddTripClicked) }
        )
    }
}