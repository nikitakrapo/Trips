package com.nikitakrapo.profile

import com.nikitakrapo.dto.Account

data class ProfileAccount(
    val email: String?
)

fun Account.toProfileModel(): ProfileAccount {
    return ProfileAccount(
        email = this.email
    )
}