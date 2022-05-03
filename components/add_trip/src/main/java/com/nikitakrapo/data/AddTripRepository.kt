package com.nikitakrapo.data

interface AddTripRepository {

    suspend fun addTrip(name: String)
}