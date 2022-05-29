package com.nikitakrapo.login.viewmodel

import androidx.lifecycle.ViewModel
import com.nikitakrapo.am.AccountManagerImpl
import com.nikitakrapo.login.LoginFeature
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    accountManager: AccountManagerImpl,
) : ViewModel() {

    val component = LoginFeature(accountManager)

    override fun onCleared() {
        component.dispose()
    }
}