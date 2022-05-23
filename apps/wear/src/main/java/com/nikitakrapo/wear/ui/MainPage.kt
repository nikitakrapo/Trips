package com.nikitakrapo.wear.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.wear.compose.foundation.CurvedRow
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.CurvedText
import androidx.wear.compose.material.Icon

@Composable
fun MainPage(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        CurvedRow(
            modifier = Modifier
                .wrapContentSize()
        ) {
            CurvedText("Sample text??")
        }
        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            onClick = {
                Toast.makeText(context, "Button clicked!", Toast.LENGTH_SHORT).show()
            }
        ) {
            Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = null)
        }
    }
}