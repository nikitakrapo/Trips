package com.nikitakrapo.am

import com.nikitakrapo.dto.Account
import kotlinx.coroutines.flow.StateFlow

interface AccountStorage {

    val account: Account?
        get() = accountStateFlow.value

    val accountStateFlow: StateFlow<Account?>
}