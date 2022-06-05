package com.nikitakrapo.profile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nikitakrapo.profile.ProfileFeature
import com.nikitakrapo.profile.impl.R
import com.nikitakrapo.trips_design.preview.ThemedPreview

@Composable
fun UnauthorizedProfileScreen(
    modifier: Modifier = Modifier,
    state: ProfileFeature.State.Unauthorized,
    openLogin: () -> Unit,
    openRegistration: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 64.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.profile_login_expanation),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(4.dp))
        
        FilledTonalButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = openLogin
        ) {
            Text(stringResource(id = R.string.profile_to_auth))
        }

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = openRegistration
        ) {
            Text(stringResource(id = R.string.profile_to_registration))
        }
    }
}

@Preview
@Composable
fun UnauthorizedProfileScreen_Preview() {
    ThemedPreview {
        UnauthorizedProfileScreen(
            state = ProfileFeature.State.Unauthorized,
            openLogin = {},
            openRegistration = {}
        )
    }
}