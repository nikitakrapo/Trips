package com.nikitakrapo.android.trips.ui.home

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nikitakrapo.android.trips.ui.NoRippleTheme

// TODO: separate from Navigation Component
@Composable
fun TripsNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    homeSections: List<HomeSections>
) {
    NavigationBar(
        modifier = modifier.shadow(elevation = 8.dp),
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        homeSections.forEach { screen ->
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                NavigationBarItem(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50)),
                    icon = {
                        Icon(
                            imageVector = screen.imageVector,
                            contentDescription = null
                        )
                    },
                    label = { Text(stringResource(screen.titleResourceId)) },
                    alwaysShowLabel = false,
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
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
}