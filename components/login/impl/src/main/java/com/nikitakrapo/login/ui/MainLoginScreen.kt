package com.nikitakrapo.login.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.nikitakrapo.login.impl.R
import com.nikitakrapo.login.MainLoginFeature.State
import com.nikitakrapo.login.MainLoginScreenDestinations
import com.nikitakrapo.login.mainLoginGraph

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainLoginScreen(
    modifier: Modifier = Modifier,
    state: State,
    onBackArrowClicked: () -> Unit,
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
                            contentDescription = stringResource(R.string.cd_go_back)
                        )
                    }
                }
            )
        }
    ) {
        val navController = rememberAnimatedNavController()
        AnimatedNavHost(
            navController,
            startDestination = MainLoginScreenDestinations.LogIn.route,
        ) {
            mainLoginGraph(navController)
        }
    }
}