package com.nikitakrapo.am

import com.google.firebase.auth.FirebaseAuth
import com.nikitakrapo.am.AccountManager.AuthorizationResult
import com.nikitakrapo.firebase.FirebaseWrapper
import kotlinx.coroutines.tasks.asDeferred
import timber.log.Timber
import javax.inject.Inject

class AccountManagerImpl @Inject constructor(
    private val accountStorageImpl: AccountStorageImpl,
) : AccountManager {

    private val auth: FirebaseAuth
        get() = FirebaseWrapper.auth

    //FIXME: move common logic
    override suspend fun createUserByEmailPassword(
        email: String,
        password: String,
    ): AuthorizationResult {
        if (email.isEmpty() || password.isEmpty()) {
            Timber.e(
                IllegalStateException(
                    "Empty credentials" +
                            "\nEmail: $email" +
                            "\nPassword: $password"
                )
            )
            return AuthorizationResult.Error(IllegalStateException("Empty credentials"))
        }
        val resultDeferred = auth.createUserWithEmailAndPassword(email, password).asDeferred()
        return try {
            val firebaseAuthResult = resultDeferred.await()
            onNewAccount()
            AuthorizationResult.Success(firebaseAuthResult.toAccount())
        } catch (e: Exception) {
            Timber.e(e, "Creating user failed")
            AuthorizationResult.Error(e)
        }
    }

    override suspend fun signInUserByEmailPassword(
        email: String,
        password: String
    ): AuthorizationResult {
        if (email.isEmpty() || password.isEmpty()) {
            Timber.e(
                IllegalStateException(
                    "Empty credentials" +
                            "\nEmail: $email" +
                            "\nPassword: $password"
                )
            )
            return AuthorizationResult.Error(IllegalStateException("Empty credentials"))
        }
        val resultDeferred = auth.signInWithEmailAndPassword(email, password).asDeferred()
        return try {
            val firebaseAuthResult = resultDeferred.await()
            Timber.d("Signing success with $firebaseAuthResult")
            onNewAccount()
            AuthorizationResult.Success(firebaseAuthResult.toAccount())
        } catch (e: Exception) {
            Timber.e(e, "Signing in user failed")
            AuthorizationResult.Error(e)
        }
    }

    private fun onNewAccount() {
        accountStorageImpl.onNewAccount()
    }
}