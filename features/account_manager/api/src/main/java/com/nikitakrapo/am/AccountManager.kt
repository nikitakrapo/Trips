package com.nikitakrapo.am

import com.nikitakrapo.dto.AccountDeletionResult
import com.nikitakrapo.dto.AuthorizationResult

interface AccountManager {

    suspend fun createUserByEmailPassword(email: String, password: String): AuthorizationResult

    suspend fun signInUserByEmailPassword(email: String, password: String): AuthorizationResult

    fun signOut()

    suspend fun deleteCurrentAccount(): AccountDeletionResult
}