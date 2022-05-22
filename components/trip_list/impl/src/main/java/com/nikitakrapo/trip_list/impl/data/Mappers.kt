package com.nikitakrapo.trip_list.impl.data

import com.nikitakrapo.trip_list.dto.TripModel
import com.nikitakrapo.trips.data.dto.Trip

fun toModel(trip: Trip): TripModel =
    TripModel(trip.name)

fun toModels(tripList: List<Trip>): List<TripModel> =
    tripList.map(::toModel)