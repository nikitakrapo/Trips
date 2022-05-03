package com.nikitakrapo.trips.components.application

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.arkivanov.mvikotlin.timetravel.store.TimeTravelStoreFactory
import com.nikitakrapo.add_trip.AddTrip
import com.nikitakrapo.add_trip.AddTripComponent
import com.nikitakrapo.trip_details.TripDetails
import com.nikitakrapo.trip_details.TripDetailsComponent
import com.nikitakrapo.trips.appComponent
import com.nikitakrapo.trips.components.add_trip.AddTrip
import com.nikitakrapo.trips.components.home.Home
import com.nikitakrapo.trips.components.login.loginGraph
import com.nikitakrapo.trips.components.trip_details.TripDetail
import com.nikitakrapo.trips.viewmodels.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

@Composable
fun TripsApp(
    viewModelFactory: ViewModelFactory
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MainSections.Home.route
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
    composable(MainSections.Home.route) {
        Home(
            viewModelFactory = viewModelFactory,
            openLogin = { navController.navigate(MainSections.Login.route) },
            openTripCard = { navController.navigate("${MainSections.TripDetails.route}/${it.name}") }, // xd
            openAddTrip = { navController.navigate(MainSections.AddTrip.route) },
        )
    }

    loginGraph(navController)

    composable(
        route = "${MainSections.TripDetails.route}/{${MainSections.TripDetails.tripNameArg}}",
        arguments = listOf(navArgument(MainSections.TripDetails.tripNameArg) {
            type = NavType.StringType
        })
    ) { backStackEntry ->
        val tripName = backStackEntry.arguments?.getString(MainSections.TripDetails.tripNameArg)
        if (tripName == null) {
            Timber.e("TripName not passed")
        }
        val tripsRepository = LocalContext.current.appComponent.tripDetailsRepository() //TODO: DI
        val component = TripDetailsComponent(
            storeFactory = TimeTravelStoreFactory(),
            componentContext = Dispatchers.Main,
            tripsRepository = tripsRepository,
            tripName = tripName ?: "" //TODO: resolve this normally
        )
        TripDetail(
            models = component.models,
            labels = component.labels,
            onBackArrowPressed = { component.accept(TripDetails.Event.BackArrowClicked) },
            onDeleteTripClicked = { component.accept(TripDetails.Event.DeleteClicked) },
            closeScreen = { navController.popBackStack() }
        )
    }

    composable(
        route = MainSections.AddTrip.route
    ) {
        val addTripRepository = LocalContext.current.appComponent.addTripRepository()
        val component = AddTripComponent(
            storeFactory = TimeTravelStoreFactory(),
            componentContext = Dispatchers.Main,
            addTripRepository = addTripRepository
        )
        AddTrip(
            models = component.models,
            labels = component.labels,
            onBackArrowPressed = { component.accept(AddTrip.Event.BackArrowClicked) },
            onNameChanged = { component.accept(AddTrip.Event.NameTextFieldChanged(it)) },
            onAddClick = { component.accept(AddTrip.Event.AddTripClicked) },
            closeScreen = { navController.popBackStack() }
        )
    }
}