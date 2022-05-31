package com.nikitakrapo.login.viewmodel

import androidx.lifecycle.ViewModel
import com.nikitakrapo.am.AccountManagerImpl
import com.nikitakrapo.login.RegistrationFeature
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    accountManager: AccountManagerImpl,
) : ViewModel() {

    val component = RegistrationFeature(accountManager)

    override fun onCleared() {
        component.dispose()
    }
}