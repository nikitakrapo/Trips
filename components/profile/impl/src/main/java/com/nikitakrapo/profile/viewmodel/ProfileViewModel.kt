package com.nikitakrapo.profile.viewmodel

import androidx.lifecycle.ViewModel
import com.nikitakrapo.am.FirebaseAccountManager
import com.nikitakrapo.am.FirebaseAccountStorage
import com.nikitakrapo.profile.ProfileFeature
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    firebaseAccountManager: FirebaseAccountManager,
    firebaseAccountStorage: FirebaseAccountStorage,
) : ViewModel() {

    val component = ProfileFeature(
        firebaseAccountManager,
        firebaseAccountStorage
    )

    override fun onCleared() {
        super.onCleared()
        component.dispose()
    }
}