package com.nikitakrapo.android.trips.data.cache.trips

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nikitakrapo.android.trips.data.cache.dto.TripEntity

@Database(entities = [TripEntity::class], version = 1)
abstract class TripsDatabase : RoomDatabase() {
    abstract fun userTripsDao(): UserTripsDao
}