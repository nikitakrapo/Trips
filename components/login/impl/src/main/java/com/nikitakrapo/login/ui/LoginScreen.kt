@file:JvmName("LogInScreenKt")

package com.nikitakrapo.login.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nikitakrapo.login.LoginFeature.State
import com.nikitakrapo.login.impl.R
import com.nikitakrapo.trips_design.preview.ThemedPreview

@Composable
fun LogInScreen(
    modifier: Modifier = Modifier,
    state: State,
    onEmailTextChanged: (String) -> Unit,
    onPasswordTextChanged: (String) -> Unit,
    onPasswordVisibilityClick: () -> Unit,
    doLogin: () -> Unit,
    openRegistration: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 54.dp),
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

        OutlinedTextField(
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
            onClick = doLogin,
            enabled = !state.isLoggingIn,
        ) {
            if (!state.isLoggingIn) {
                Text(text = stringResource(R.string.login_button_text))
            } else {
                CircularProgressIndicator(modifier = Modifier.size(32.dp))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row {
            Text(text = stringResource(R.string.login_not_registered_yet_question))

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                modifier = Modifier.clickable(onClick = openRegistration),
                text = stringResource(R.string.login_sign_up),
                color = Color.Blue,
            )
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    ThemedPreview {
        LogInScreen(
            state = State(),
            onEmailTextChanged = {},
            onPasswordTextChanged = {},
            onPasswordVisibilityClick = {},
            doLogin = { /*TODO*/ },
            openRegistration = { /*TODO*/ },
        )
    }
}