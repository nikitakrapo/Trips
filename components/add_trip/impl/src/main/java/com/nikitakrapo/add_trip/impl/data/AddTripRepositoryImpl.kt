package com.nikitakrapo.add_trip.impl.data

import com.nikitakrapo.data.AddTripRepository
import com.nikitakrapo.trips.data.TripsRepository
import javax.inject.Inject

class AddTripRepositoryImpl @Inject constructor(
    private val tripsRepository: TripsRepository,
) : AddTripRepository {
    override suspend fun addTrip(name: String) {
        tripsRepository.addTrip(name)
    }
}