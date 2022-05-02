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
import com.nikitakrapo.android.trips.ui.trip.TripDetail.Model
import com.nikitakrapo.android.trips.ui.trip.TripDetailStore.Label
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetail(
    modifier: Modifier = Modifier,
    models: Flow<Model>,
    labels: Flow<Label>,
    onBackArrowPressed: () -> Unit,
    onDeleteTripClicked: () -> Unit,
    closeScreen: () -> Unit, //TODO: it should not be here
) {
    val modelsState = models.collectAsState(Model())
    val labelsState = labels.collectAsState(initial = null)

    LaunchedEffect(labelsState.value) {
        when (labelsState.value) {
            is Label.CloseScreen -> closeScreen()
            null -> {}
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(modelsState.value.name) //TODO: handle overflow
                },
                navigationIcon = {
                    IconButton(onClick = onBackArrowPressed) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = stringResource(R.string.cd_back_icon))
                    }
                },
                actions = {
                    IconButton(onClick = onDeleteTripClicked) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = stringResource(R.string.cd_delete_trip))
                    }
                }
            )
        }
    ) {
    }
}
