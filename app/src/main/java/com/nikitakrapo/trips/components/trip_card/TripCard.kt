package com.nikitakrapo.trips.components.trip_card

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.nikitakrapo.trips.R
import com.nikitakrapo.trips.data.dto.Trip
import com.nikitakrapo.trips_design.theme.TripsTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TripCard(
    modifier: Modifier = Modifier,
    tripCardState: TripCardState,
    onClick: (Trip) -> Unit = {},
    onLongClick: (Trip) -> Unit = {},
) {
    Card(
        modifier = modifier
            .combinedClickable(
                onClick = { onClick(tripCardState.trip) },
                onLongClick = { onLongClick(tripCardState.trip) },
            ),
        shape = RoundedCornerShape(10.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            val (
                tripTitle,
                moreButton,
            ) = createRefs()

            Text(
                modifier = Modifier
                    .constrainAs(tripTitle) {
                        top.linkTo(parent.top, margin = 16.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                    },
                text = tripCardState.trip.name,
                style = MaterialTheme.typography.titleLarge
            )

            IconButton(
                modifier = Modifier
                    .constrainAs(moreButton) {
                        top.linkTo(tripTitle.top)
                        bottom.linkTo(tripTitle.bottom)
                        end.linkTo(parent.end)
                    },
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = stringResource(id = R.string.cd_more_icon)
                )
            }
        }
    }
}

@Preview
@Composable
fun TripCardPreview() {
    TripsTheme {
        Surface(
            modifier = Modifier
                .height(744.dp)
                .width(360.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
            ) {
                TripCard(
                    modifier = Modifier,
                    tripCardState = TripCardState(
                        Trip(
                            name = "Ekaterinburg - Tbilisi",
                        )
                    )
                )
            }
        }
    }
}
