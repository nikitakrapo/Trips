package com.nikitakrapo.profile.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.nikitakrapo.profile.ProfileFeature

@Composable
fun AuthorizedProfileScreen(
    modifier: Modifier = Modifier,
    state: ProfileFeature.State.Authorized,
    signOut: () -> Unit,
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val (emailText, signOutButton) = createRefs()
        Text(
            modifier = Modifier.constrainAs(emailText) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            },
            text = state.authorizedUser.email.toString(),
            style = MaterialTheme.typography.titleLarge
        )

        Button(
            modifier = Modifier.constrainAs(signOutButton) {
                top.linkTo(emailText.bottom)
            },
            onClick = signOut
        ) {
            Text("Sign out")
        }
    }
}