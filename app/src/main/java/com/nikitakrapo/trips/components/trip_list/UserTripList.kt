package com.nikitakrapo.trips.components.trip_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.nikitakrapo.trips.R
import com.nikitakrapo.trips.ThemedPreview
import com.nikitakrapo.trips.components.trip_card.TripCard
import com.nikitakrapo.trips.components.trip_card.TripCardState
import com.nikitakrapo.trips.data.dto.Trip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserTripList(
    modifier: Modifier = Modifier,
    userTripListUiState: UserTripListUiState,
    onTripCardClick: (Trip) -> Unit = {},
    onTripsSwipeRefresh: () -> Unit = {},
    onAddTripClick: () -> Unit = {},
    onLongTripClick: (Trip) -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTripClick
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.cd_add_trip)
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            if (userTripListUiState.showProgressBar) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center),
                )
            } else {
                SwipeRefresh(
                    modifier = Modifier
                        .fillMaxSize(),
                    state = rememberSwipeRefreshState(
                        isRefreshing = userTripListUiState.swipeRefreshRefreshing
                    ),
                    onRefresh = onTripsSwipeRefresh,
                ) {
                    if (!userTripListUiState.hasPlannedTrips) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(32.dp))
                            Text(
                                text = stringResource(R.string.trip_list_empty),
                                style = MaterialTheme.typography.titleLarge,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentPadding = PaddingValues(8.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(userTripListUiState.tripList) { trip ->
                                TripCard(
                                    onLongClick = onLongTripClick,
                                    tripCardState = TripCardState(trip),
                                    onClick = onTripCardClick
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}

@Preview
@Composable
fun TripsPreview_Loading() {
    ThemedPreview {
        UserTripList(
            modifier = Modifier,
            userTripListUiState = UserTripListUiState(
                showProgressBar = true,
                tripList = listOf()
            )
        )
    }
}

@Preview
@Composable
fun TripsPreview_FilledList() {
    ThemedPreview {
        UserTripList(
            modifier = Modifier,
            userTripListUiState = UserTripListUiState(
                showProgressBar = false,
                tripList = listOf(
                    Trip(
                        name = "Ekaterinburg - Tbilisi",
                    ),
                    Trip(
                        name = "Tbilisi - Sochi",
                    ),
                    Trip(
                        name = "Ekaterinburg - Bern",
                    ),
                )
            )
        )
    }
}

@Preview
@Composable
fun TripsPreview_EmptyList() {
    ThemedPreview {
        UserTripList(
            modifier = Modifier,
            userTripListUiState = UserTripListUiState(
                showProgressBar = false,
                tripList = emptyList()
            )
        )
    }
}
