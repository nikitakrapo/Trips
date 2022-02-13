package com.nikitakrapo.android.trips.ui.home

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.nikitakrapo.android.trips.R

sealed class HomeSection(val route: String, @StringRes val titleResourceId: Int, val imageVector: ImageVector) {
    object Explore : HomeSection("explore", R.string.explore_screen_title, Icons.Filled.Search)
    object Trips : HomeSection("trips", R.string.trips_screen_title, Icons.Filled.Place)
    object Profile : HomeSection("profile", R.string.profile_screen_title, Icons.Filled.Person)
}
