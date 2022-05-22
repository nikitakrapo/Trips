package com.nikitakrapo.trip_list.impl.data

import com.nikitakrapo.trip_list.data.TripListRepository
import com.nikitakrapo.trip_list.dto.TripModel
import com.nikitakrapo.trips.data.TripsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TripListRepositoryImpl @Inject constructor(
    private val tripsRepository: TripsRepository
) : TripListRepository {
    override suspend fun removeTrip(name: String) {
        tripsRepository.removeTrip(name)
    }

    override suspend fun getTripList(): List<TripModel> {
        return tripsRepository.getTrips().map(::toModel)
    }

    override fun getTripListFlow(): Flow<List<TripModel>> {
        return tripsRepository.getTripsFlow().map(::toModels)
    }
}