package com.nikitakrapo.android.trips.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nikitakrapo.android.trips.ui.screens.ExploreScreen
import com.nikitakrapo.android.trips.ui.screens.ProfileScreen
import com.nikitakrapo.android.trips.ui.screens.Screen
import com.nikitakrapo.android.trips.ui.screens.TripsScreen

@Composable
fun TripsApp() {
    val screens = listOf(
        Screen.Explore,
        Screen.Trips,
        Screen.Profile
    )
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            TripsBottomNav(
                navController = navController,
                screens = screens
            )
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Trips.route,
            Modifier.padding(innerPadding)
        ) {
            screens.forEach { screen ->
                composable(screen.route) {
                    when (screen) {
                        Screen.Explore -> ExploreScreen()
                        Screen.Trips -> TripsScreen()
                        Screen.Profile -> ProfileScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun TripsBottomNav(
    modifier: Modifier = Modifier,
    navController: NavController,
    screens: List<Screen>
) {
    BottomNavigation(
        modifier = modifier.shadow(elevation = 8.dp),
        backgroundColor = MaterialTheme.colors.surface
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        screens.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = screen.imageVector,
                        contentDescription = null
                    )
                },
                label = { Text(stringResource(screen.titleResourceId)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.medium),
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}