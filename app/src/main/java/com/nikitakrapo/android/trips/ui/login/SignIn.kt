package com.nikitakrapo.android.trips.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignIn(
    navigateToRegistration: () -> Unit,
    goBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar {
                IconButton(onClick = { goBack() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            }
        }
    ) {
        Column {
            Text(text = "sign in")
            Button(onClick = navigateToRegistration) {
                Text("TO REG")
            }
        }
    }
}