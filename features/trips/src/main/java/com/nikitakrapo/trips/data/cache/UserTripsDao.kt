package com.nikitakrapo.trips.data.cache

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserTripsDao {
    @Query("SELECT * FROM TripEntity")
    fun getAll(): List<TripEntity>

    @Query("SELECT * FROM TripEntity")
    fun getTripsFlow(): Flow<List<TripEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTrip(trip: TripEntity)

    @Delete
    fun delete(trip: TripEntity)
}