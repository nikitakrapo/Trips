package com.nikitakrapo.android.trips.ui

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
import com.nikitakrapo.android.trips.appComponent
import com.nikitakrapo.android.trips.viewmodels.ViewModelFactory
import com.nikitakrapo.trips_design.theme.TripsTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

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

                    TripsApp(
                        viewModelFactory
                    )
                }
            }

            LaunchedEffect(Unit) {
                showSplashScreen = false
            }
        }
    }
}