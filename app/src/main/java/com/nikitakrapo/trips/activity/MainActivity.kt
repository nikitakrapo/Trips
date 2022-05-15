package com.nikitakrapo.trips.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.nikitakrapo.trips.appComponent
import com.nikitakrapo.trips.components.application.TripsApp
import com.nikitakrapo.trips_design.theme.TripsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var showSplashScreen: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO: use it later to await prefetching
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { showSplashScreen }

        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setContent {
            TripsTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val systemUiController = rememberSystemUiController()
                    val isLight = !isSystemInDarkTheme()

                    val statusBarColor = MaterialTheme.colorScheme.background
                    val navBarColor = MaterialTheme.colorScheme.surfaceVariant
                    SideEffect {
                        systemUiController.setStatusBarColor(
                            color = statusBarColor,
                            darkIcons = isLight
                        )

                        systemUiController.setNavigationBarColor(
                            color = navBarColor,
                            darkIcons = isLight
                        )
                    }

                    TripsApp()
                }
            }

            LaunchedEffect(Unit) {
                showSplashScreen = false
            }
        }
    }
}