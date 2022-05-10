package com.nikitakrapo.trips.components.trip_details

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.nikitakrapo.trip_details.TripDetails
import com.nikitakrapo.trip_details.TripDetails.Event
import com.nikitakrapo.trip_details.TripDetails.Label
import com.nikitakrapo.trip_details.TripDetails.Model
import com.nikitakrapo.trips.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetail(
    modifier: Modifier = Modifier,
    component: TripDetails,
    callbacks: TripDetails.ViewCallbacks,
) {
    val modelsState = component.models.collectAsState(Model())
    val labelsState = component.labels.collectAsState(initial = null)

    LaunchedEffect(labelsState.value) {
        when (labelsState.value) {
            is Label.CloseScreen -> callbacks.closeScreen() //FIXME: it should not be here
            null -> {}
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            SmallTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = {
                        component.accept(Event.BackArrowClicked)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.cd_back_icon)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        component.accept(Event.MoreClicked)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = stringResource(R.string.cd_more_icon)
                        )
                    }

                    DropdownMenu(
                        expanded = modelsState.value.isDropdownMenuOpened,
                        onDismissRequest = { component.accept(Event.OutsideOfDropdownClicked) }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(text = stringResource(id = R.string.delete_trip))
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = stringResource(R.string.cd_delete_trip)
                                )
                            },
                            onClick = { component.accept(Event.DeleteClicked) }
                        )
                    }
                }
            )
        }
    ) {
        ConstraintLayout {
            val (title) = createRefs()

            Text(
                modifier = Modifier
                    .constrainAs(title) {
                        start.linkTo(parent.start, 16.dp)
                    },
                text = modelsState.value.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}
