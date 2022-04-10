package com.nikitakrapo.android.trips.ui.home.trips

import java.util.*

data class Trip(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val dates: String = ""
)
