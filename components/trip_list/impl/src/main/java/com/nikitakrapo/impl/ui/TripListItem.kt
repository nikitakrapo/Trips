package com.nikitakrapo.impl.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.nikitakrapo.impl.R
import com.nikitakrapo.trip_list.dto.TripModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TripListItem(
    modifier: Modifier = Modifier,
    tripModel: TripModel,
    onClick: (String) -> Unit = {},
) {
    Card(
        modifier = modifier
            .combinedClickable(
                onClick = { onClick(tripModel.name) },
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
                text = tripModel.name,
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
