package com.nikitakrapo.validators

import com.nikitakrapo.add_trip.AddTripFeature

interface TripNameTextValidator {

    fun validate(name: String): AddTripFeature.TripNameError?
}