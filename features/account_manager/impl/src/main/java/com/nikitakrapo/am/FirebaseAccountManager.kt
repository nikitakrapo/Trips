package com.nikitakrapo.am

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.nikitakrapo.am.AccountManager.AuthorizationResult
import com.nikitakrapo.firebase.FirebaseProvider
import kotlinx.coroutines.tasks.asDeferred
import timber.log.Timber
import javax.inject.Inject

class FirebaseAccountManager @Inject constructor(
    private val firebaseProvider: FirebaseProvider,
) : AccountManager {

    private val auth: FirebaseAuth
        get() = firebaseProvider.auth

    override suspend fun createUserByEmailPassword(
        email: String,
        password: String,
    ): AuthorizationResult {
        checkCredentials(email, password)?.let { return AuthorizationResult.Error(it) }
        return auth.createUserWithEmailAndPassword(email, password).awaitAuthorizationResult()
    }

    override suspend fun signInUserByEmailPassword(
        email: String,
        password: String
    ): AuthorizationResult {
        checkCredentials(email, password)?.let { return AuthorizationResult.Error(it) }
        return auth.signInWithEmailAndPassword(email, password).awaitAuthorizationResult()
    }

    private suspend fun Task<AuthResult>.awaitAuthorizationResult(): AuthorizationResult {
        return try {
            val firebaseAuthResult = this.asDeferred().await()
            AuthorizationResult.Success(firebaseAuthResult.toAccount())
        } catch (e: Exception) {
            AuthorizationResult.Error(e)
        }
    }

    private fun checkCredentials(email: String, password: String): Exception? {
        return if (email.isEmpty() || password.isEmpty()) {
            val exception = IllegalStateException(
                "Empty credentials" +
                        "\nEmail: $email" +
                        "\nPassword: $password"
            )
            Timber.e(exception)
            exception
        } else {
            null
        }
    }
}