package com.nikitakrapo.trip_list.data

import com.nikitakrapo.trip_list.dto.TripModel
import kotlinx.coroutines.flow.Flow

interface TripListRepository {
    fun getTripListFlow(): Flow<List<TripModel>>
    suspend fun getTripList(): List<TripModel>
    suspend fun removeTrip(name: String)
}