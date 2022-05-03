package com.nikitakrapo.trips.components.trip_details

import com.nikitakrapo.trips.data.TripsRepository
import com.nikitakrapo.data.TripDetailsRepository
import javax.inject.Inject

class TripDetailsRepository @Inject constructor(
    private val tripsRepository: TripsRepository,
) : TripDetailsRepository {
    override suspend fun removeTrip(tripName: String) {
        tripsRepository.removeTrip(tripName)
    }
}