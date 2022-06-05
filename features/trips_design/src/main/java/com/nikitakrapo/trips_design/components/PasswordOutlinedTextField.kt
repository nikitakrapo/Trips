package com.nikitakrapo.trips_design.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.nikitakrapo.trips_design.R

@Composable
fun PasswordOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val textFieldCd = stringResource(R.string.trips_design_cd_password_input)
    val toggleButtonCd = stringResource(R.string.trips_design_cd_toggle_password_visibility)
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .semantics { contentDescription = textFieldCd },
        isError = isError,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }
            val description = stringResource(
                if (passwordVisible) {
                    R.string.login_cd_show_password
                } else {
                    R.string.login_cd_hide_password
                }
            )

            IconButton(
                modifier = Modifier.semantics {
                    contentDescription = toggleButtonCd
                },
                onClick = {
                    passwordVisible = !passwordVisible
                }
            ) {
                Icon(imageVector = image, description)
            }
        },
        label = { Text(text = stringResource(R.string.password_text_field_label)) },
        value = value,
        onValueChange = onValueChange,
    )
}
