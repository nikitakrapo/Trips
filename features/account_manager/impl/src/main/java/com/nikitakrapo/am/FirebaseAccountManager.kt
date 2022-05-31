package com.nikitakrapo.am

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.nikitakrapo.dto.AccountDeletionException
import com.nikitakrapo.dto.AccountDeletionResult
import com.nikitakrapo.dto.AuthorizationResult
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

    override fun signOut() {
        auth.signOut()
    }

    override suspend fun deleteCurrentAccount(): AccountDeletionResult {
        return auth.currentUser?.let { user ->
            try {
                user.delete().asDeferred().await()
                AccountDeletionResult.Success
            } catch (e: Exception) {
                AccountDeletionResult.Error(e.toAccountDeletionException())
            }
        } ?: AccountDeletionResult.Error(AccountDeletionException.NoCurrentUserException)
    }

    private fun Exception.toAccountDeletionException(): AccountDeletionException =
        when (this) {
            is FirebaseAuthRecentLoginRequiredException ->
                AccountDeletionException.RecentLoginRequiredException
            else -> AccountDeletionException.UnknownException
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