package com.nikitakrapo.am

import com.nikitakrapo.dto.Account
import com.nikitakrapo.firebase.FirebaseProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAccountStorage @Inject constructor(
    firebaseProvider: FirebaseProvider
) : AccountStorage {

    init {
        firebaseProvider.auth.addAuthStateListener {
            val newUser = it.currentUser
            Timber.d("Auth state changed. \nCurrent user: $newUser")
            _accountFlow.value = newUser?.toAccount()
        }
    }

    private val _accountFlow = MutableStateFlow(firebaseProvider.auth.currentUser?.toAccount())
    override val accountStateFlow: StateFlow<Account?> = _accountFlow
}