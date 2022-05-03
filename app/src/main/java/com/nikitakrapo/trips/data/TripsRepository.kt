package com.nikitakrapo.trips.data

import com.nikitakrapo.trips.data.cache.TripEntity
import com.nikitakrapo.trips.data.cache.UserTripsDao
import com.nikitakrapo.trips.components.trip_list.Trip
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TripsRepository @Inject constructor(
    private val userTripsDao: UserTripsDao
) {
    suspend fun addTrip(name: String) {
        userTripsDao.addTrip(TripEntity(name))
    }

    suspend fun removeTrip(name: String) {
        userTripsDao.delete(TripEntity(name))
    }

    suspend fun getTrips(): List<Trip> {
        return userTripsDao.getAll().map {
            Trip(name = it.name)
        }
    }

    fun getTripsFlow(): Flow<List<Trip>> {
        return userTripsDao.getTripsFlow().map {
            it.map { entity ->
                Trip(name = entity.name)
            }
        }
    }
}