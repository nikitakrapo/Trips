package com.nikitakrapo.trip_list.data

import com.nikitakrapo.trip_list.dto.TripModel
import kotlinx.coroutines.flow.Flow

interface TripListRepository {
    suspend fun removeTrip(name: String)
    fun getTripListFlow(): Flow<List<TripModel>>
}