package com.nikitakrapo.validators

import com.nikitakrapo.add_trip.AddTrip

interface TripNameTextValidator {

    fun validate(name: String): AddTrip.TripNameError?
}