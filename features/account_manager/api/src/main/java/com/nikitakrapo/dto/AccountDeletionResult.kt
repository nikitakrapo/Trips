package com.nikitakrapo.dto

sealed class AccountDeletionResult {
    object Success : AccountDeletionResult()
    class Error(val exception: AccountDeletionException) : AccountDeletionResult()
}

sealed class AccountDeletionException {
    object RecentLoginRequiredException : AccountDeletionException()
    object NoCurrentUserException : AccountDeletionException()
    object UnknownException : AccountDeletionException()
}