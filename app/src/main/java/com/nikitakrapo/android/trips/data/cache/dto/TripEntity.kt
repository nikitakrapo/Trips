package com.nikitakrapo.android.trips.data.cache.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TripEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

//    @ColumnInfo(name = "start_date")
//    val startDate: Date,
//
//    @ColumnInfo(name = "end_date")
//    val endDate: Date,
)