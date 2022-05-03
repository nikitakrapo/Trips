package com.nikitakrapo.android.trips.ui.add_trip

import com.nikitakrapo.android.trips.data.TripsRepository
import com.nikitakrapo.data.AddTripRepository
import javax.inject.Inject

class AddTripRepository @Inject constructor(
    private val tripsRepository: TripsRepository,
) : AddTripRepository {
    override suspend fun addTrip(name: String) {
        tripsRepository.addTrip(name)
    }
}