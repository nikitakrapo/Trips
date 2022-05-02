package com.nikitakrapo.android.trips.ui.trip

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.nikitakrapo.android.trips.R
import com.nikitakrapo.android.trips.ui.trip.TripDetail.Event

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetail(
    modifier: Modifier = Modifier,
    tripDetail: TripDetail, //TODO: accept only state and callbacks, not component itself
    closeScreen: () -> Unit,
) {
    val state = tripDetail.models.collectAsState(TripDetail.Model())
    val labels = tripDetail.labels.collectAsState(initial = null)

    LaunchedEffect(labels.value) {
        when (labels.value) {
            is TripDetailStore.Label.CloseScreen -> closeScreen()
            null -> {}
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(state.value.name) //TODO: handle overflow
                },
                navigationIcon = {
                    IconButton(onClick = { tripDetail.accept(Event.BackArrowClicked) }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = stringResource(R.string.cd_back_icon))
                    }
                },
                actions = {
                    IconButton(onClick = { tripDetail.accept(Event.DeleteClicked) }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = stringResource(R.string.cd_delete_trip))
                    }
                }
            )
        }
    ) {
    }
}
