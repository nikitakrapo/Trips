package com.nikitakrapo.android.trips.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nikitakrapo.android.trips.ui.home.sections.Explore
import com.nikitakrapo.android.trips.ui.home.sections.Profile
import com.nikitakrapo.android.trips.ui.home.sections.Trips
import com.nikitakrapo.android.trips.ui.theme.TripsTheme

@Composable
fun Home() {
    val screens = listOf(
        HomeSection.Explore,
        HomeSection.Trips,
        HomeSection.Profile
    )
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            TripsBottomNav(
                navController = navController,
                homeSections = screens
            )
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = HomeSection.Trips.route,
            Modifier.padding(innerPadding)
        ) {
            screens.forEach { screen ->
                composable(screen.route) {
                    when (screen) {
                        HomeSection.Explore -> Explore()
                        HomeSection.Trips -> Trips()
                        HomeSection.Profile -> Profile()
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
    homeSections: List<HomeSection>
) {
    NavigationBar(
        modifier = modifier.shadow(elevation = 8.dp),
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        homeSections.forEach { screen ->
            CompositionLocalProvider(LocalRippleTheme provides NavigationBarItemCustomRippleTheme) {
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

object NavigationBarItemCustomRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor(): Color = Color.Transparent

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0f, 0f, 0f, 0f)
}

@Preview
@Composable
fun TripsBottomNav_Preview() {
    TripsTheme {
        Surface() {

        }
    }
}