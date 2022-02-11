package com.nikitakrapo.android.trips.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.nikitakrapo.android.trips.R
import com.nikitakrapo.android.trips.ui.theme.DarkGray
import com.nikitakrapo.android.trips.ui.theme.NikisBlack
import com.nikitakrapo.android.trips.ui.theme.TripsTheme
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun TripCard(
    modifier: Modifier = Modifier,
    openTripScreen: (Unit) -> Unit = {}
) {
    Card(
        modifier = modifier,
        elevation = 8.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            val (
                tripTitle,
                tripDates,
                moreButton,
            ) = createRefs()

            Text(
                modifier = Modifier
                    .constrainAs(tripTitle) {
                        top.linkTo(parent.top, margin = 16.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                    },
                text = "Ekaterinburg - Tbilisi",
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            )

            Text(
                modifier = Modifier
                    .constrainAs(tripDates) {
                        top.linkTo(tripTitle.bottom)
                        start.linkTo(tripTitle.start)
                    },
                text = "5 Jul - 12 Jul",
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
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

@Composable
fun AccommodationsListItem(
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (image, title, location, dates) = createRefs()

        GlideImage(
            modifier = Modifier
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .size(width = 96.dp, height = 56.dp)
                .clip(shape = RoundedCornerShape(5.dp)),
            imageModel = "",
            contentScale = ContentScale.FillBounds
        )

        Text(
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(image.top)
                    start.linkTo(image.end, margin = 8.dp)
                },
            text = "Ateshgah Residence", //FIXME
            fontSize = 14.sp,
            color = NikisBlack
        )

        Text(
            modifier = Modifier
                .constrainAs(location) {
                    top.linkTo(title.bottom)
                    start.linkTo(title.start)
                },
            text = "Barvikha st. 42, 620017", //FIXME
            fontSize = 12.sp,
            color = DarkGray
        )

        Text(
            modifier = Modifier
                .constrainAs(dates) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(title.start)
                },
            text = "5 Jul 14:00 - 8 Jul 14:00", //FIXME
            fontSize = 9.sp,
            color = DarkGray
        )
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
            color = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
            ) {
                TripCard(
                    modifier = Modifier
                )
            }
        }
    }
}

@Preview
@Composable
fun AccommodationsListIemPreview() {
    TripsTheme {
        Surface(
            modifier = Modifier
                .height(744.dp)
                .width(328.dp),
            color = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
            ) {
                AccommodationsListItem()
            }
        }
    }
}