package com.nikitakrapo.trips.components.add_trip

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.nikitakrapo.add_trip.AddTrip
import com.nikitakrapo.add_trip.AddTrip.Label
import com.nikitakrapo.add_trip.AddTrip.Model
import com.nikitakrapo.trips.R
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class, androidx.compose.material.ExperimentalMaterialApi::class)
@Composable
fun AddTrip(
    modifier: Modifier = Modifier,
    models: Flow<Model>,
    labels: Flow<Label>,
    onBackArrowPressed: () -> Unit = {},
    onNameChanged: (String) -> Unit = {},
    onAddClick: () -> Unit = {},
    closeScreen: () -> Unit = {}, //TODO: it shouldn't be here
) {
    val modelState = models.collectAsState(initial = Model())
    val labelFlow = labels.collectAsState(initial = null)

    LaunchedEffect(labelFlow.value) {
        when (labelFlow.value) {
            is Label.CloseScreen -> closeScreen()
            null -> {}
        }
    }

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
                    IconButton(onClick = onBackArrowPressed) {
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
                value = modelState.value.nameText,
                onValueChange = onNameChanged,
                label = {
                    Text("Trip name")
                },
                isError = modelState.value.nameError != null,
                singleLine = true,
                maxLines = 1,
                keyboardActions = KeyboardActions(
                    onDone = {
                        localFocusManager.clearFocus()
                    }
                ),
            )
            if (modelState.value.nameError != null) {
                val errorText = getTextForNameError(modelState.value)
                Text(
                    modifier = Modifier.constrainAs(nameFieldError) {
                        top.linkTo(nameField.bottom, 4.dp)
                        start.linkTo(nameField.start)
                    },
                    text = errorText,
                    color = Color.Red
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
                enabled = modelState.value.isAddButtonEnabled
            ) {
                Text(
                    text = if (!modelState.value.isAdding) {
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
fun getTextForNameError(model: Model): String {
    val error = model.nameError
    return when (error) {
        is AddTrip.TripNameError.InvalidCharacters -> {
            stringResource(R.string.error_incorrect_characters)
        }
        is AddTrip.TripNameError.TooShort -> {
            stringResource(R.string.error_too_short, error.minChars)
        }
        is AddTrip.TripNameError.TooLong -> {
            stringResource(R.string.error_too_long, error.maxChars) //TODO: should be handled by ux
        }
        null -> ""
    }
}