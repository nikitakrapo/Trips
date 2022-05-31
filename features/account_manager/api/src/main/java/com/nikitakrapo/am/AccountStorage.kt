package com.nikitakrapo.am

import com.nikitakrapo.dto.Account
import kotlinx.coroutines.flow.StateFlow

interface AccountStorage {

    val accountStateFlow: StateFlow<Account?>
}