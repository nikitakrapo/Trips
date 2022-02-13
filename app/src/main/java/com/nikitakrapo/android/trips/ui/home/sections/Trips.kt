package com.nikitakrapo.android.trips.ui.home.sections

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Trips(
    modifier: Modifier = Modifier
) {
    Text(
        modifier = Modifier.fillMaxSize(),
        text = "trips"
    )
}