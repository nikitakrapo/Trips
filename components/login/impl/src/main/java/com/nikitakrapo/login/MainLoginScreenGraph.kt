package com.nikitakrapo.login

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.nikitakrapo.login.MainLoginFeature.LoginScreen
import com.nikitakrapo.login.ui.LogInScreen
import com.nikitakrapo.login.viewmodel.LoginViewModel

@OptIn(ExperimentalAnimationApi::class)
internal fun NavGraphBuilder.mainLoginGraph(navController: NavController) {
    composable(route = MainLoginScreenDestinations.LogIn.route) {
        val loginViewModel: LoginViewModel = hiltViewModel()
        val component = loginViewModel.component
        val uiState = loginViewModel.component.state.collectAsState()

        LaunchedEffect(Unit) {
            loginViewModel.component.news.collect { news ->
                when (news) {
                    is LoginFeature.News.CloseScreen -> {
                        navController.popBackStack(MainLoginScreenDestinations.LogIn.route, true)
                    }
                }
            }
        }

        LogInScreen(
            state = uiState.value,
            onEmailTextChanged = { email ->
                component.accept(LoginFeature.Intent.ChangeEmailText(email))
            },
            onPasswordTextChanged = { password ->
                component.accept(LoginFeature.Intent.ChangePasswordText(password))
            },
            onPasswordVisibilityClick = {
                component.accept(LoginFeature.Intent.ChangePassportVisibility)
            },
            doLogin = {
                component.accept(LoginFeature.Intent.PerformLogin)
            },
            openRegistration = { /*TODO*/ },
            openTermsOfService = { /*TODO*/ },
        )
    }
}

internal sealed class MainLoginScreenDestinations(
    val tripScreen: LoginScreen,
    val route: String,
) {
    object LogIn : MainLoginScreenDestinations(LoginScreen.LogIn, "log_in")
    object Register : MainLoginScreenDestinations(LoginScreen.Register, "register")
}