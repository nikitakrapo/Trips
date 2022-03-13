package com.nikitakrapo.android.trips.data.cache.trips

import androidx.room.*
import com.nikitakrapo.android.trips.data.cache.dto.TripEntity

@Dao
interface UserTripsDao {
    @Query("SELECT * FROM TripEntity")
    fun getAll(): List<TripEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTrip(trip: TripEntity)

    @Delete
    fun delete(trip: TripEntity)
}