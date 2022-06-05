package com.nikitakrapo.login.viewmodel

import androidx.lifecycle.ViewModel
import com.nikitakrapo.am.FirebaseAccountManager
import com.nikitakrapo.login.RegistrationFeature
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    accountManager: FirebaseAccountManager,
) : ViewModel() {

    val component = RegistrationFeature(accountManager)

    override fun onCleared() {
        component.dispose()
    }
}