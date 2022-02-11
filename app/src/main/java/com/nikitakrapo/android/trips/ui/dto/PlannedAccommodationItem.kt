package com.nikitakrapo.android.trips.ui.dto

import java.util.*

data class PlannedAccommodationItem(
    val title: String,
    val imageUrl: String,
    val dates: Pair<Date, Date>
)