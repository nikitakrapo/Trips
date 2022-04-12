package com.nikitakrapo.android.trips.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.nikitakrapo.android.trips.R
import com.nikitakrapo.trips_design.theme.TripsTheme

@Composable
fun Profile(
    modifier: Modifier = Modifier,
    navigateToLogin: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        ProfileHeader(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            navigateToLogin = navigateToLogin
        )
    }
}

@Composable
fun ProfileHeader(
    modifier: Modifier = Modifier,
    navigateToLogin: () -> Unit
) {
    ConstraintLayout(modifier = modifier) {
        val (profilePicture, nickname) = createRefs()

        Image(
            modifier = Modifier
                .constrainAs(profilePicture) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .size(64.dp)
                .clip(RoundedCornerShape(32.dp))
                .clickable { navigateToLogin() },
            painter = ColorPainter(Color.Gray),
            contentDescription = stringResource(R.string.cd_profile_picture)
        )
        Text(
            modifier = Modifier
                .constrainAs(nickname) {
                    top.linkTo(profilePicture.bottom, margin = 8.dp)
                    start.linkTo(profilePicture.start)
                },
            text = "Nikita", //FIXME
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    TripsTheme {
        Surface(
            modifier = Modifier
                .height(744.dp)
                .width(360.dp),
            color = MaterialTheme.colors.background
        ) {
            Profile(navigateToLogin = {})
        }
    }
}