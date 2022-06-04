package com.nikitakrapo.profile.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nikitakrapo.profile.ProfileFeature.State

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    state: State,
    openAuthorization: () -> Unit,
    signOut: () -> Unit,
    deleteAccount: () -> Unit,
) {
    when (state) {
        is State.Authorized -> AuthorizedProfileScreen(
            modifier = modifier,
            state = state,
            signOut = signOut,
        )
        is State.Unauthorized -> UnauthorizedProfileScreen(
            modifier = modifier,
            state = state,
            openAuthorization = openAuthorization
        )
    }
}