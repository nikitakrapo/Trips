package com.nikitakrapo.profile.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.nikitakrapo.profile.ProfileFeature.State
import com.nikitakrapo.profile.impl.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    state: State,
    openAuthorization: () -> Unit,
    signOut: () -> Unit,
    deleteAccount: () -> Unit,
) {
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {},
                actions = {
                    AnimatedVisibility(
                        visible = state.authorizedUser != null,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        IconButton(onClick = deleteAccount) {
                            Icon(
                                Icons.Filled.Delete,
                                stringResource(R.string.cd_profile_delete_account)
                            )
                        }
                    }
                }
            )
        },
        modifier = modifier.fillMaxSize()
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val (emailText, signOutButton, toAuthButton) = createRefs()
            Text(
                modifier = Modifier.constrainAs(emailText) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
                text = state.authorizedUser?.email.toString(),
                style = MaterialTheme.typography.titleLarge
            )

            AnimatedVisibility(
                modifier = Modifier.constrainAs(toAuthButton) {
                    top.linkTo(emailText.bottom)
                },
                visible = state.authorizedUser == null,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Button(onClick = openAuthorization) {
                    Text("To authorization")
                }
            }

            AnimatedVisibility(
                modifier = Modifier.constrainAs(signOutButton) {
                    top.linkTo(emailText.bottom)
                },
                visible = state.authorizedUser != null,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Button(onClick = signOut) {
                    Text("Sign out")
                }
            }
        }

    }
}