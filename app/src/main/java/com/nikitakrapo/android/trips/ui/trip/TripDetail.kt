package com.nikitakrapo.android.trips.ui.trip

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nikitakrapo.android.trips.R
import com.nikitakrapo.android.trips.ThemedPreview
import com.nikitakrapo.android.trips.ui.home.trips.Trip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetail(
    trip: Trip,
    onBack: () -> Unit = {}
) {
    Scaffold(
        // TODO: move topAppBar to separate function
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .shadow(elevation = 4.dp),
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.cd_back_icon)
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        }
    ) {

    }
}

@Preview
@Composable
fun TripDetailPreview() {
    ThemedPreview {
        TripDetail(
            trip = Trip()
        )
    }
}