package com.nikitakrapo.profile.viewmodel

import androidx.lifecycle.ViewModel
import com.nikitakrapo.am.AccountManagerImpl
import com.nikitakrapo.am.AccountStorageImpl
import com.nikitakrapo.profile.ProfileFeature
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    accountManagerImpl: AccountManagerImpl,
    accountStorageImpl: AccountStorageImpl,
) : ViewModel() {

    val component = ProfileFeature(
        accountManagerImpl,
        accountStorageImpl
    )

    override fun onCleared() {
        super.onCleared()
        component.dispose()
    }
}