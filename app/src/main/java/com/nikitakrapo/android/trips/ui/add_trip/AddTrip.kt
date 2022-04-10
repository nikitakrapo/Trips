package com.nikitakrapo.android.trips.ui.add_trip

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nikitakrapo.android.trips.R
import com.nikitakrapo.android.trips.ThemedPreview

@OptIn(ExperimentalMaterial3Api::class, androidx.compose.material.ExperimentalMaterialApi::class)
@Composable
fun AddTrip(
    modifier: Modifier = Modifier,
    uiState: AddTripUiState,
    onNameChanged: (String) -> Unit,
    onBackArrow: () -> Unit = {}
) {
    val localFocusManager = LocalFocusManager.current
    Scaffold(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    localFocusManager.clearFocus()
                })
            },
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackArrow) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(
                                id = R.string.cd_back_icon
                            )
                        )
                    }
                },
                title = {
                    Text(text = stringResource(id = R.string.trip_adding))
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            TextField(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(400.dp),
                value = uiState.name,
                onValueChange = onNameChanged,
                singleLine = true,
                maxLines = 1,
                keyboardActions = KeyboardActions(
                    onDone = {
                        localFocusManager.clearFocus()
                    }
                )
            )
        }
    }
}

@Preview
@Composable
fun AddTripBottomSheet_Preview() {
    ThemedPreview {
        //AddTrip()
    }
}