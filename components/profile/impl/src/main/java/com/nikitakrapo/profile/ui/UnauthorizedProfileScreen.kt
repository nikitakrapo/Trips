package com.nikitakrapo.profile.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nikitakrapo.profile.ProfileFeature

@Composable
fun UnauthorizedProfileScreen(
    modifier: Modifier = Modifier,
    state: ProfileFeature.State.Unauthorized,
    openLogin: () -> Unit,
    openRegistration: () -> Unit,
) {
    Column(modifier = modifier) {
        Button(onClick = openLogin) {
            Text("To authorization")
        }

        Button(onClick = openRegistration) {
            Text("To registration")
        }
    }
}