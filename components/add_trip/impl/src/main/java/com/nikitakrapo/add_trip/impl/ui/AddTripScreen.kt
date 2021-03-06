package com.nikitakrapo.add_trip.impl.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.nikitakrapo.add_trip.AddTripFeature
import com.nikitakrapo.add_trip.AddTripFeature.State
import com.nikitakrapo.add_trip.impl.R

@OptIn(ExperimentalMaterial3Api::class, androidx.compose.material.ExperimentalMaterialApi::class)
@Composable
fun AddTripScreen(
    modifier: Modifier = Modifier,
    state: State,
    onNameChanged: (String) -> Unit = {},
    onAddClick: () -> Unit = {},
    onBackArrowPressed: () -> Unit = {},
) {
    BackHandler(onBack = onBackArrowPressed)
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
                    IconButton(
                        modifier = Modifier.testTag("top_bar_back"),
                        onClick = onBackArrowPressed
                    ) {
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
            val (nameField, nameFieldError, fabNext) = createRefs()

            OutlinedTextField(
                modifier = Modifier
                    .constrainAs(nameField) {
                        top.linkTo(parent.top, 8.dp)
                        start.linkTo(parent.start, 16.dp)
                        end.linkTo(parent.end, 16.dp)
                        width = Dimension.fillToConstraints
                    },
                value = state.nameText,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = MaterialTheme.colorScheme.onSurface
                ),
                onValueChange = onNameChanged,
                label = {
                    Text("Trip name")
                },
                isError = state.nameError != null,
                singleLine = true,
                maxLines = 1,
                keyboardActions = KeyboardActions(
                    onDone = {
                        localFocusManager.clearFocus()
                    }
                ),
            )
            val nameErrorVisible = state.nameError != null
            AnimatedVisibility(
                modifier = Modifier.constrainAs(nameFieldError) {
                    top.linkTo(nameField.bottom, 4.dp)
                    start.linkTo(nameField.start)
                },
                visible = nameErrorVisible,
                enter = slideInVertically(),
                exit = slideOutVertically(),
            ) {
                val errorText = getTextForNameError(state)
                Text(
                    text = errorText,
                    color = MaterialTheme.colorScheme.error
                )
            }

            ElevatedButton(
                modifier = Modifier
                    .constrainAs(fabNext) {
                        bottom.linkTo(parent.bottom, 16.dp)
                        start.linkTo(parent.start, 32.dp)
                        end.linkTo(parent.end, 32.dp)
                        width = Dimension.fillToConstraints
                    },
                onClick = onAddClick,
                enabled = state.isAddButtonEnabled
            ) {
                Text(
                    text = if (!state.isAdding) {
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

@Composable
fun getTextForNameError(state: State): String {
    val error = state.nameError
    return when (error) {
        is AddTripFeature.TripNameError.InvalidCharacters -> {
            stringResource(R.string.error_incorrect_characters)
        }
        is AddTripFeature.TripNameError.TooShort -> {
            stringResource(R.string.error_too_short, error.minChars)
        }
        is AddTripFeature.TripNameError.TooLong -> {
            stringResource(R.string.error_too_long, error.maxChars)
        }
        null -> ""
    }
}