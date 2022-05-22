package com.nikitakrapo.trips.data.dto

import java.util.*

data class Trip(
    val id: UUID = UUID.randomUUID(),
    val name: String = "",
    val dates: String = ""
)
