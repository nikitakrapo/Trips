package com.nikitakrapo.login.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.nikitakrapo.login.RegistrationFeature.State
import com.nikitakrapo.login.impl.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    state: State,
    onEmailTextChanged: (String) -> Unit,
    onPasswordTextChanged: (String) -> Unit,
    onPasswordVisibilityClick: () -> Unit,
    onRegisterClicked: () -> Unit,
    onBackArrowPressed: () -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SmallTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackArrowPressed) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.cd_go_back)
                        )
                    }
                }
            )
        },
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 54.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                isError = state.loginError != null,
                label = { Text(text = stringResource(R.string.email_text_field_label)) },
                value = state.emailText,
                onValueChange = onEmailTextChanged,
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField( //TODO: maybe move out password textfield logic
                modifier = Modifier.fillMaxWidth(),
                isError = state.loginError != null,
                visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (state.isPasswordVisible) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    }
                    val description = stringResource(
                        if (state.isPasswordVisible) {
                            R.string.login_cd_show_password
                        } else {
                            R.string.login_cd_hide_password
                        }
                    )

                    IconButton(onClick = onPasswordVisibilityClick) {
                        Icon(imageVector = image, description)
                    }
                },
                label = { Text(text = stringResource(R.string.password_text_field_label)) },
                value = state.passwordText,
                onValueChange = onPasswordTextChanged,
            )

            Spacer(modifier = Modifier.height(8.dp))

            AnimatedVisibility(
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.Start),
                enter = slideInVertically(),
                exit = slideOutVertically(),
                visible = state.loginError != null
            ) {
                Text(
                    modifier = Modifier.wrapContentWidth(),
                    text = state.loginError.orEmpty(),
                    color = MaterialTheme.colorScheme.error,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            ElevatedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp),
                onClick = onRegisterClicked,
                enabled = !state.isRegistering,
            ) {
                if (!state.isRegistering) {
                    Text(text = stringResource(R.string.register_button_text))
                } else {
                    CircularProgressIndicator(modifier = Modifier.size(32.dp))
                }
            }
        }
    }
}