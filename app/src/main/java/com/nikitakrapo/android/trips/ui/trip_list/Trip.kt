package com.nikitakrapo.android.trips.ui.trip_list

import java.util.*

data class Trip(
    val id: UUID = UUID.randomUUID(),
    val name: String = "",
    val dates: String = ""
)
