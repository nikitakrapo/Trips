package com.nikitakrapo.android.trips.data.trips

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TripEntity(
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String,
)