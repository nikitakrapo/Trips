package com.nikitakrapo.am

import com.nikitakrapo.dto.Account

interface AccountManager {

    suspend fun performAuthByEmailPassword(email: String, password: String)

    sealed class AuthResult {
        class Success(account: Account) : AuthResult()
        class Error(error: Exception) : AuthResult()
    }
}