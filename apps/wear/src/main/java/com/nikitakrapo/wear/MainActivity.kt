package com.nikitakrapo.wear

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.nikitakrapo.trips_design.theme.TripsTheme
import com.nikitakrapo.wear.ui.MainPage

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TripsTheme {
                MainPage()
            }
        }
    }
}