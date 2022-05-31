package com.nikitakrapo.am

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.nikitakrapo.dto.Account

fun AuthResult.toAccount(): Account? {
    return user?.toAccount()
}

fun FirebaseUser.toAccount(): Account {
    return Account(
        uid = uid,
        isEmailConfirmed = isEmailVerified,
        email = email,
        displayName = displayName
    )
}