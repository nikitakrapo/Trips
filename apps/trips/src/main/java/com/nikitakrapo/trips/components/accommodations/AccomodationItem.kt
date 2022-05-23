package com.nikitakrapo.trips.components.accommodations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nikitakrapo.trips.R
import com.nikitakrapo.trips_design.theme.TripsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccommodationsListItem(
    modifier: Modifier = Modifier,
    accommodationItemState: AccommodationItemState
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        var expanded by remember { mutableStateOf(false) }
        val dropDownAngle: Float by animateFloatAsState(
            targetValue = if (!expanded) 0f else 180f
        )

        Column(
            modifier = Modifier
                .padding(8.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                AnimatedVisibility(
                    visible = !expanded,
                ) {
                    Image(
                        modifier = Modifier
                            .testTag("mainImage")
                            .size(width = 100.dp, height = 56.dp)
                            .clip(shape = RoundedCornerShape(5.dp)),
                        painter = ColorPainter(MaterialTheme.colorScheme.primary),
                        contentDescription = stringResource(id = R.string.cd_accommodation_image)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = accommodationItemState.title,
                        style = MaterialTheme.typography.titleSmall,
                    )

                    Text(
                        text = accommodationItemState.location,
                        fontSize = 12.sp,
                    )

                    Text(
                        text = accommodationItemState.dates,
                        fontSize = 9.sp,
                    )
                }

                IconButton(
                    modifier = Modifier,
                    onClick = { expanded = !expanded },
                ) {
                    Icon(
                        modifier = Modifier
                            .rotate(dropDownAngle)
                            .testTag("expandButton"),
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = null
                    )
                }
            }

            AnimatedVisibility(
                visible = expanded,
            ) {
                Text(
                    modifier = Modifier
                        .testTag("expandedImage")
                        .height(140.dp)
                        .background(MaterialTheme.colorScheme.outline)
                        .fillMaxWidth(),
                    text = "BEBRIK"
                )
            }
        }
    }
}

@Preview
@Composable
fun AccommodationsListItemPreview() {
    TripsTheme {
        Surface(
            modifier = Modifier
                .size(
                    width = 360.dp,
                    height = 700.dp
                ),
            color = MaterialTheme.colorScheme.surface
        ) {
            AccommodationsListItem(
                accommodationItemState = AccommodationItemState(
                    imageUrl = "",
                    title = "Ateshgah Residence",
                    location = "Barvikha st. 42, 620017",
                    dates = "5 Jul 14:00 - 8 Jul 14:00"
                )
            )

        }
    }
}
