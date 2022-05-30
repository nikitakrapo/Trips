package com.nikitakrapo.am

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.nikitakrapo.dto.Account

fun AuthResult.toAccount(): Account? {
    return user?.let { firebaseUser ->
        Account(
            uid = firebaseUser.uid,
            email = firebaseUser.email,
            displayName = firebaseUser.displayName,
        )
    }
}

fun FirebaseUser.toAccount(): Account {
    return Account(
        uid = uid,
        email = email,
        displayName = displayName
    )
}