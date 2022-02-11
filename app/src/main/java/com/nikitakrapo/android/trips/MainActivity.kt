package com.nikitakrapo.android.trips

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.nikitakrapo.android.trips.ui.TripsApp
import com.nikitakrapo.android.trips.ui.theme.TripsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setContent {
            TripsTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val systemUiController = rememberSystemUiController()
                    val isLight = MaterialTheme.colors.isLight
                    val surfaceColor = MaterialTheme.colors.surface
                    val backgroundColor = MaterialTheme.colors.background

                    SideEffect {
                        systemUiController.setStatusBarColor(
                            color = backgroundColor,
                            darkIcons = isLight
                        )

                        systemUiController.setNavigationBarColor(
                            color = surfaceColor,
                            darkIcons = isLight
                        )
                    }

                    TripsApp()
                }
            }
        }
    }
}