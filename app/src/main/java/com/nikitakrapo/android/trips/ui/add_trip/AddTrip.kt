package com.nikitakrapo.android.trips.ui.add_trip

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.nikitakrapo.android.trips.R
import com.nikitakrapo.android.trips.ThemedPreview

@OptIn(ExperimentalMaterial3Api::class, androidx.compose.material.ExperimentalMaterialApi::class)
@Composable
fun AddTrip(
    modifier: Modifier = Modifier,
    uiState: AddTripUiState,
    onNameChanged: (String) -> Unit = {},
    onAddClick: () -> Unit = {},
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
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            val (nameField, fabNext) = createRefs()

            OutlinedTextField(
                modifier = Modifier
                    .constrainAs(nameField) {
                        top.linkTo(parent.top, 8.dp)
                        start.linkTo(parent.start, 16.dp)
                        end.linkTo(parent.end, 16.dp)
                        width = Dimension.fillToConstraints
                    },
                value = uiState.nameTextField,
                onValueChange = onNameChanged,
                label = {
                    Text("Trip name")
                },
                singleLine = true,
                maxLines = 1,
                keyboardActions = KeyboardActions(
                    onDone = {
                        localFocusManager.clearFocus()
                    }
                ),
            )

            ElevatedButton(
                modifier = Modifier
                    .constrainAs(fabNext) {
                        bottom.linkTo(parent.bottom, 16.dp)
                        start.linkTo(parent.start, 32.dp)
                        end.linkTo(parent.end, 32.dp)
                        width = Dimension.fillToConstraints
                    },
                onClick = onAddClick,
                enabled = !uiState.isAddFabLoading
            ) {
                Text(
                    text = if (!uiState.isAddFabLoading) {
                        stringResource(R.string.add_trip)
                    } else {
                        stringResource(R.string.adding_progress)
                    },
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun AddTripBottomSheet_Preview_Empty() {
    ThemedPreview {
        AddTrip(
            uiState = AddTripUiState()
        )
    }
}

@Preview
@Composable
fun AddTripBottomSheet_Preview_Loading() {
    ThemedPreview {
        AddTrip(
            uiState = AddTripUiState(
                isAddFabLoading = true
            )
        )
    }
}
