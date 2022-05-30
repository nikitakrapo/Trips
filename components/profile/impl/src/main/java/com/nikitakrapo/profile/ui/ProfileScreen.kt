package com.nikitakrapo.profile.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nikitakrapo.profile.ProfileFeature.State

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    state: State,
    openAuthorization: () -> Unit,
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(text = state.authorizedUser.toString())

        Button(onClick = openAuthorization) {
            Text("To authorization")
        }
    }
}