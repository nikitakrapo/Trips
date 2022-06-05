package com.nikitakrapo.profile.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nikitakrapo.profile.ProfileAccount
import com.nikitakrapo.profile.ProfileFeature
import com.nikitakrapo.profile.impl.R
import com.nikitakrapo.trips_design.preview.ThemedPreview

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AuthorizedProfileScreen(
    modifier: Modifier = Modifier,
    state: ProfileFeature.State.Authorized,
    logout: () -> Unit,
    openSettings: () -> Unit,
    deleteAccount: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier
                .padding(8.dp),
            text = state.authorizedUser.email.toString(),
            style = MaterialTheme.typography.titleLarge
        )

        ListItem(
            modifier = Modifier
                .clickable(onClick = openSettings),
            text = { Text(stringResource(R.string.profile_settings)) },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = stringResource(R.string.profile_cd_settings)
                )
            }
        )

        ListItem(
            modifier = Modifier
                .clickable(onClick = logout),
            text = { Text(stringResource(R.string.profile_logout)) },
            icon = {
                Icon(
                    imageVector = Icons.Filled.ExitToApp,
                    contentDescription = stringResource(R.string.profile_cd_logout)
                )
            }
        )

        ListItem(
            modifier = Modifier
                .clickable(onClick = deleteAccount),
            text = {
                Text(
                    text = stringResource(R.string.profile_delete_account),
                    color = MaterialTheme.colorScheme.error
                )
            },
            secondaryText = {
                Text(
                    text = stringResource(R.string.profile_delete_account_reauth),
                    color = MaterialTheme.colorScheme.error
                )
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = stringResource(R.string.profile_cd_delete_account),
                    tint = MaterialTheme.colorScheme.error,
                )
            }
        )
    }
}

@Preview
@Composable
fun AuthorizedProfileScreen_Preview() {
    ThemedPreview {
        AuthorizedProfileScreen(
            state = ProfileFeature.State.Authorized(
                authorizedUser = ProfileAccount(
                    email = "sample.email@gmail.com",
                    name = "Nikita Krapotkin"
                )
            ),
            logout = {},
            openSettings = {},
            deleteAccount = {}
        )
    }
}