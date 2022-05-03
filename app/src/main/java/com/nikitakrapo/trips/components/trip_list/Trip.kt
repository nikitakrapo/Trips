package com.nikitakrapo.trips.components.trip_list

import java.util.*

data class Trip(
    val id: UUID = UUID.randomUUID(),
    val name: String = "",
    val dates: String = ""
)
