@file:JvmName("LogInScreenKt")

package com.nikitakrapo.login.ui

import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.nikitakrapo.impl.R
import com.nikitakrapo.login.LoginFeature.State
import com.nikitakrapo.trips_design.preview.ThemedPreview

@Composable
fun LogInScreen(
    modifier: Modifier = Modifier,
    state: State,
    onLoginTextChanged: (String) -> Unit,
    onPasswordTextChanged: (String) -> Unit,
    doLogin: () -> Unit,
    openRegistration: () -> Unit,
    openTermsOfService: () -> Unit,
) {
    ConstraintLayout(
        modifier = modifier,
    ) {
        val (
            emailField,
            passwordField,
            loginButton,
            toRegistrationButton,
            termsButton
        ) = createRefs()

        OutlinedTextField(
            modifier = Modifier.constrainAs(emailField) {
                top.linkTo(parent.top)
                bottom.linkTo(passwordField.top)
                centerHorizontallyTo(parent)
            },
            label = { Text(text = stringResource(R.string.email_text_field_label)) },
            value = state.emailText,
            onValueChange = onLoginTextChanged,
        )

        OutlinedTextField(
            modifier = Modifier.constrainAs(passwordField) {
                top.linkTo(emailField.bottom)
                bottom.linkTo(loginButton.top)
                centerHorizontallyTo(parent)
            },
            label = { Text(text = stringResource(R.string.password_text_field_label)) },
            value = state.passwordText,
            onValueChange = onPasswordTextChanged,
        )

        OutlinedButton(
            modifier = Modifier.constrainAs(loginButton) {
                top.linkTo(passwordField.bottom)
                bottom.linkTo(parent.bottom)
                centerHorizontallyTo(parent)
            },
            onClick = doLogin,
        ) {
            Text(text = stringResource(R.string.login_button_text))
        }

        createVerticalChain(emailField, passwordField, loginButton, chainStyle = ChainStyle.Packed)
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    ThemedPreview {
        LogInScreen(
            state = State(),
            onLoginTextChanged = {},
            onPasswordTextChanged = {},
            doLogin = { /*TODO*/ },
            openRegistration = { /*TODO*/ }) {
        }
    }
}