package com.nikitakrapo.add_trip.impl.data

import com.nikitakrapo.add_trip.AddTripFeature.TripNameError
import com.nikitakrapo.validators.TripNameTextValidator
import javax.inject.Inject

class TripNameTextValidatorImpl(
    private val config: Config,
) : TripNameTextValidator {

    @Inject
    constructor() : this(Config())

    override fun validate(name: String): TripNameError? {
        val invalidCharacters = getIncorrectCharacters(name)
        val tooShort = name.length < config.minLen
        val tooLong = name.length > config.maxLen
        return when {
            tooShort -> TripNameError.TooShort(config.minLen)
            tooLong -> TripNameError.TooLong(config.maxLen)
            invalidCharacters.isNotEmpty() -> TripNameError.InvalidCharacters(invalidCharacters)
            else -> null
        }
    }

    private fun getIncorrectCharacters(name: String): Set<Char> {
        return name.filter { config.invalidCharacters.contains(it) }.toSet()
    }

    class Config(
        val minLen: Int = DEFAULT_MIN_LEN,
        val maxLen: Int = DEFAULT_MAX_LEN,
        val invalidCharacters: Set<Char> = DEFAULT_FORBIDDEN_CHARACTERS,
    )

    companion object {
        const val DEFAULT_MIN_LEN = 1
        const val DEFAULT_MAX_LEN = 12
        val DEFAULT_FORBIDDEN_CHARACTERS = setOf('?', '\'', '%', ';', '*', '!', '/', '\\')
    }
}