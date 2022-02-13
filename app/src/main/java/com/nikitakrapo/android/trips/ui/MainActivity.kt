package com.nikitakrapo.android.trips.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.nikitakrapo.android.trips.appComponent
import com.nikitakrapo.android.trips.ui.theme.TripsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setContent {
            TripsTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val systemUiController = rememberSystemUiController()
                    val isLight = !isSystemInDarkTheme()
                    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
                    val backgroundColor = MaterialTheme.colorScheme.background

                    SideEffect {
                        systemUiController.setStatusBarColor(
                            color = backgroundColor,
                            darkIcons = isLight
                        )

                        systemUiController.setNavigationBarColor(
                            color = surfaceVariant,
                            darkIcons = isLight
                        )
                    }

                    TripsApp()
                }
            }
        }
    }
}