package com.nikitakrapo.data

interface TripDetailsRepository {

    suspend fun removeTrip(tripName: String)
}