package com.nikitakrapo.trip_list.impl.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.nikitakrapo.trip_list.R
import com.nikitakrapo.trip_list.component.TripListFeature.State
import com.nikitakrapo.trips_design.components.FadingAnimatedVisibility

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun TripList(
    modifier: Modifier = Modifier,
    state: State,
    onAddTripClicked: () -> Unit,
    onSwipeRefresh: () -> Unit,
    onTripClicked: (String) -> Unit,
) {
    val initLoading = state.tripList == null
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        floatingActionButton = {
            AnimatedVisibility(
                visible = !initLoading,
                enter = scaleIn(),
            ) {
                FloatingActionButton(
                    onClick = onAddTripClicked,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(id = R.string.cd_add_trip)
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            FadingAnimatedVisibility(visible = initLoading) {
                CircularProgressIndicator(modifier = Modifier.size(64.dp))
            }
        }

        SwipeRefresh(
            modifier = Modifier
                .fillMaxSize(),
            state = rememberSwipeRefreshState(
                isRefreshing = state.isRefreshing
            ),
            onRefresh = onSwipeRefresh,
        ) {
            val shouldShowList = state.tripList?.isNotEmpty() == true
            FadingAnimatedVisibility(visible = shouldShowList) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    state.tripList?.let {
                        items(it) { trip ->
                            TripListItem(
                                tripModel = trip,
                                onClick = { onTripClicked(trip.name) }
                            )
                        }
                    }
                }
            }
            val emptyListAfterRefresh = state.tripList?.isEmpty() == true
            FadingAnimatedVisibility(visible = emptyListAfterRefresh) {
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
            }
        }
    }
}
