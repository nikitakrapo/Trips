package com.nikitakrapo.am

import com.nikitakrapo.dto.Account

interface AccountManager {

    suspend fun createUserByEmailPassword(email: String, password: String): AuthorizationResult

    suspend fun signInUserByEmailPassword(email: String, password: String): AuthorizationResult

    sealed class AuthorizationResult {
        class Success(val account: Account?) : AuthorizationResult()
        class Error(val error: Exception) : AuthorizationResult()
    }
}