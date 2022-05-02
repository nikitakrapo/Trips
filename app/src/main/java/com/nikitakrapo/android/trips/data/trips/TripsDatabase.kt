package com.nikitakrapo.android.trips.data.trips

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TripEntity::class], version = 1)
abstract class TripsDatabase : RoomDatabase() {
    abstract fun userTripsDao(): UserTripsDao

    companion object {
        const val NAME = "trips-database"
    }
}