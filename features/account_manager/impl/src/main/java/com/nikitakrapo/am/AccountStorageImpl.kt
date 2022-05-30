package com.nikitakrapo.am

import com.nikitakrapo.dto.Account
import com.nikitakrapo.firebase.FirebaseWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AccountStorageImpl @Inject constructor() : AccountStorage {
    private val auth
        get() = FirebaseWrapper.auth

    private val _accountFlow = MutableStateFlow(auth.currentUser?.toAccount())
    override val accountFlow: StateFlow<Account?> = _accountFlow

    fun onNewAccount() {
        _accountFlow.value = auth.currentUser?.toAccount()
    }
}