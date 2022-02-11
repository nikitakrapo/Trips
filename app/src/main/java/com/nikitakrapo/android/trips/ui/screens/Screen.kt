package com.nikitakrapo.android.trips.ui.screens

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.nikitakrapo.android.trips.R

sealed class Screen(val route: String, @StringRes val titleResourceId: Int, val imageVector: ImageVector) {
    object Explore : Screen("explore", R.string.explore_screen_title, Icons.Filled.Search)
    object Trips : Screen("trips", R.string.trips_screen_title, Icons.Filled.Place)
    object Profile : Screen("profile", R.string.profile_screen_title, Icons.Filled.Person)
}
