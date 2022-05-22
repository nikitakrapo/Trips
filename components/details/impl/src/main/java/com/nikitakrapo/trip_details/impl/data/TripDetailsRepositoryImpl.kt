package com.nikitakrapo.trip_details.impl.data

import com.nikitakrapo.data.TripDetailsRepository
import com.nikitakrapo.trips.data.TripsRepository
import javax.inject.Inject

class TripDetailsRepositoryImpl @Inject constructor(
    private val tripsRepository: TripsRepository,
): TripDetailsRepository {
    override suspend fun removeTrip(tripName: String) {
        tripsRepository.removeTrip(tripName)
    }
}