package com.nikitakrapo.android.trips.ui.trip

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.nikitakrapo.android.trips.R
import com.nikitakrapo.android.trips.ThemedPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetail(
    tripDetailUiState: TripDetailUiState,
    onBack: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
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
                        text = tripDetailUiState.tripName,
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
        TripDetail(TripDetailUiState("Sochi vacation"))
    }
}