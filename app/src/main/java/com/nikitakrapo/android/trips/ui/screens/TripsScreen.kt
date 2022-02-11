package com.nikitakrapo.android.trips.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun TripsScreen(
    modifier: Modifier = Modifier
) {
    Text(
        modifier = Modifier.fillMaxSize(),
        text = "trips"
    )
}