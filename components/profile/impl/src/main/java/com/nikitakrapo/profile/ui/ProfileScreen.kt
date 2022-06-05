package com.nikitakrapo.profile.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nikitakrapo.profile.ProfileFeature.State

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    state: State,
    openLogin: () -> Unit,
    openRegistration: () -> Unit,
    signOut: () -> Unit,
    openSettings: () -> Unit,
    deleteAccount: () -> Unit,
) {
    when (state) {
        is State.Authorized -> AuthorizedProfileScreen(
            modifier = modifier,
            state = state,
            logout = signOut,
            openSettings = openSettings,
            deleteAccount = deleteAccount,
        )
        is State.Unauthorized -> UnauthorizedProfileScreen(
            modifier = modifier,
            state = state,
            openLogin = openLogin,
            openRegistration = openRegistration
        )
    }
}