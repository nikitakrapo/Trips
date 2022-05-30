package com.nikitakrapo.profile

import com.nikitakrapo.dto.Account

data class ProfileAccount(
    val email: String?
)

fun Account.toProfileAccount(): ProfileAccount {
    return ProfileAccount(
        email = this.email
    )
}