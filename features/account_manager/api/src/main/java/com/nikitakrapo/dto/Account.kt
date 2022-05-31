package com.nikitakrapo.dto

class Account(
    val uid: String,
    val isEmailConfirmed: Boolean,
    val email: String? = null,
    val displayName: String? = null
)