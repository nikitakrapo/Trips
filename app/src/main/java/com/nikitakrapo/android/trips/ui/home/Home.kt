package com.nikitakrapo.android.trips.ui.home

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
import com.nikitakrapo.android.trips.ui.home.sections.Profile
import com.nikitakrapo.android.trips.ui.home.sections.trips.Trips

@OptIn(
    ExperimentalAnimationApi::class,
    com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi::class,
    androidx.compose.material.ExperimentalMaterialApi::class
)
@Composable
fun Home(
    navigateToLogin: () -> Unit
) {
    val screens = listOf(
        HomeScreen.Trips,
        HomeScreen.Profile
    )
    val navController = rememberAnimatedNavController()
    Scaffold(
        bottomBar = {
            TripsNavigationBar(
                navController = navController,
                homeScreens = screens
            )
        }
    ) { innerPadding ->
        AnimatedNavHost(
            navController,
            startDestination = HomeScreen.Trips.route,
            Modifier.padding(innerPadding)
        ) {
            screens.forEach { screen ->
                when (screen) {

                    HomeScreen.Trips -> composable(
                        route = screen.route,
                        enterTransition = { fadeIn(animationSpec = tween(0)) },
                        exitTransition = { fadeOut(animationSpec = tween(0)) },
                        popEnterTransition = { fadeIn(animationSpec = tween(0)) },
                        popExitTransition = { fadeOut(animationSpec = tween(0)) },
                    ) { Trips() }

                    HomeScreen.Profile -> composable(
                        route = screen.route,
                        enterTransition = { fadeIn(animationSpec = tween(0)) },
                        exitTransition = { fadeOut(animationSpec = tween(0)) },
                        popEnterTransition = { fadeIn(animationSpec = tween(0)) },
                        popExitTransition = { fadeOut(animationSpec = tween(0)) },
                    ) {
                        Profile(
                            navigateToLogin = navigateToLogin
                        )
                    }
                }
            }
        }
    }
}
