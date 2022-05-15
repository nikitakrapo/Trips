package com.nikitakrapo.impl.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.nikitakrapo.impl.R
import com.nikitakrapo.trip_list.component.TripList
import com.nikitakrapo.trip_list.component.TripList.Event.AddTripClicked
import com.nikitakrapo.trip_list.component.TripList.Event.SwipeRefreshPulled
import com.nikitakrapo.trip_list.component.TripList.Event.TripClicked
import com.nikitakrapo.trip_list.component.TripList.Model

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripList(
    modifier: Modifier = Modifier,
    component: TripList,
) {
    val userTripListUiState = component.models.collectAsState(initial = Model())

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { component.accept(AddTripClicked) }
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
                    isRefreshing = userTripListUiState.value.isRefreshing
                ),
                onRefresh = { component.accept(SwipeRefreshPulled) },
            ) {
                if (userTripListUiState.value.tripList.isEmpty()) {
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
                        items(userTripListUiState.value.tripList) { trip ->
                            TripListItem(
                                tripModel = trip,
                                onClick = { component.accept(TripClicked(trip.name)) }
                            )
                        }
                    }
                }
            }
        }
    }
}
//
//@Preview
//@Composable
//fun TripsPreview_Loading() {
//    ThemedPreview {
//        UserTripList(
//            modifier = Modifier,
//            userTripListUiState = UserTripListUiState(
//                showProgressBar = true,
//                tripList = listOf()
//            )
//        )
//    }
//}
//
//@Preview
//@Composable
//fun TripsPreview_FilledList() {
//    ThemedPreview {
//        UserTripList(
//            modifier = Modifier,
//            userTripListUiState = UserTripListUiState(
//                showProgressBar = false,
//                tripList = listOf(
//                    Trip(
//                        name = "Ekaterinburg - Tbilisi",
//                    ),
//                    Trip(
//                        name = "Tbilisi - Sochi",
//                    ),
//                    Trip(
//                        name = "Ekaterinburg - Bern",
//                    ),
//                )
//            )
//        )
//    }
//}
//
//@Preview
//@Composable
//fun TripsPreview_EmptyList() {
//    ThemedPreview {
//        UserTripList(
//            modifier = Modifier,
//            userTripListUiState = UserTripListUiState(
//                showProgressBar = false,
//                tripList = emptyList()
//            )
//        )
//    }
//}
