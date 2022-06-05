package com.nikitakrapo.dto

sealed class AuthorizationResult {
    class Success(val account: Account?) : AuthorizationResult()
    class Error(val error: Exception) : AuthorizationResult()
}