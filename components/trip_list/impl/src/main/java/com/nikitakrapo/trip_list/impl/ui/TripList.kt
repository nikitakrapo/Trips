package com.nikitakrapo.trip_list.impl.ui

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
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.nikitakrapo.trip_list.R
import com.nikitakrapo.trip_list.component.TripList.State

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripList(
    modifier: Modifier = Modifier,
    state: State,
    onAddTripClicked: () -> Unit,
    onSwipeRefresh: () -> Unit,
    onTripClicked: (String) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTripClicked,
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
            SwipeRefresh(
                modifier = Modifier
                    .fillMaxSize(),
                state = rememberSwipeRefreshState(
                    isRefreshing = state.isRefreshing
                ),
                onRefresh = onSwipeRefresh,
            ) {
                if (state.tripList.isEmpty()) {
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
                        items(state.tripList) { trip ->
                            TripListItem(
                                tripModel = trip,
                                onClick = { onTripClicked(trip.name) }
                            )
                        }
                    }
                }
            }
        }
    }
}
