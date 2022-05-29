package com.nikitakrapo.am

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class AccountManagerImpl @Inject constructor() : AccountManager {

    override suspend fun performAuthByEmailPassword(email: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(email, password).result.user
    }
}