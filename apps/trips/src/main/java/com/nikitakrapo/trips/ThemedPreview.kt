package com.nikitakrapo.trips

import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nikitakrapo.trips_design.theme.TripsTheme

@Composable
fun ThemedPreview(
    width: Dp = 360.dp,
    height: Dp = 700.dp,
    content: @Composable () -> Unit
) {
    TripsTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.size(
                width = width,
                height = height
            )
        ) {
            content()
        }
    }
}