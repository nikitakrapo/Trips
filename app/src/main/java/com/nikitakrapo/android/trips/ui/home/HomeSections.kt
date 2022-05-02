package com.nikitakrapo.android.trips.ui.home

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector
import com.nikitakrapo.android.trips.R

sealed class HomeSections(val route: String, @StringRes val titleResourceId: Int, val imageVector: ImageVector) {
    object Trips : HomeSections("trips", R.string.trips_screen_title, Icons.Filled.Place)
    object Profile : HomeSections("profile", R.string.profile_screen_title, Icons.Filled.Person)
}
