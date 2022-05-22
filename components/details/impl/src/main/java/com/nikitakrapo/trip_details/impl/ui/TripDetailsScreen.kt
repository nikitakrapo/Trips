package com.nikitakrapo.trip_details.impl.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.nikitakrapo.trip_details.R
import com.nikitakrapo.trip_details.TripDetailsFeature.State

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetailsScreen(
    modifier: Modifier = Modifier,
    state: State,
    onBackArrowClicked: () -> Unit,
    onMoreClicked: () -> Unit,
    onMoreDismiss: () -> Unit,
    onDeleteClicked: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            SmallTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackArrowClicked) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.cd_back_icon)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onMoreClicked) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = stringResource(R.string.cd_more_icon)
                        )
                    }

                    DropdownMenu(
                        expanded = state.isDropdownMenuOpened,
                        onDismissRequest = onMoreDismiss
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(text = stringResource(id = R.string.delete_trip))
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = stringResource(R.string.cd_delete_trip)
                                )
                            },
                            onClick = onDeleteClicked
                        )
                    }
                }
            )
        }
    ) {
        ConstraintLayout {
            val (title) = createRefs()

            Text(
                modifier = Modifier
                    .constrainAs(title) {
                        start.linkTo(parent.start, 16.dp)
                    },
                text = state.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}
