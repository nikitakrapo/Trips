package com.nikitakrapo.login.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nikitakrapo.login.LoginFeature
import com.nikitakrapo.login.RegistrationFeature
import com.nikitakrapo.login.ui.LogInScreen
import com.nikitakrapo.login.ui.RegistrationScreen
import com.nikitakrapo.login.viewmodel.LoginViewModel
import com.nikitakrapo.login.viewmodel.RegistrationViewModel
import com.nikitakrapo.navigation.NavigationDestination

sealed class AuthorizationDestinations(override val route: String) : NavigationDestination {
    object LogIn : AuthorizationDestinations("login")
    object Registration : AuthorizationDestinations("registration")
}

fun NavGraphBuilder.authorizationNavGraph(navController: NavController) {
    composable(route = AuthorizationDestinations.LogIn.route) {
        val loginViewModel: LoginViewModel = hiltViewModel()
        val component = loginViewModel.component
        val uiState = loginViewModel.component.state.collectAsState()

        LaunchedEffect(Unit) {
            loginViewModel.component.news.collect { news ->
                when (news) {
                    is LoginFeature.News.CloseScreen -> {
                        navController.popBackStack(
                            AuthorizationDestinations.LogIn.route,
                            true
                        )
                    }
                    is LoginFeature.News.OpenRegistration -> {
                        navController.navigate(AuthorizationDestinations.Registration.route)
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
            onLoginClicked = {
                component.accept(LoginFeature.Intent.PerformLogin)
            },
            openRegistration = {
                component.accept(LoginFeature.Intent.OpenRegistration)
            },
            onBackArrowPressed = {
                component.accept(LoginFeature.Intent.CloseScreen)
            },
        )
    }

    composable(route = AuthorizationDestinations.Registration.route) {
        val registrationViewModel: RegistrationViewModel = hiltViewModel()
        val component = registrationViewModel.component
        val uiState = component.state.collectAsState()

        LaunchedEffect(Unit) {
            component.news.collect { news ->
                when (news) {
                    is RegistrationFeature.News.CloseScreen -> {
                        navController.popBackStack(
                            AuthorizationDestinations.Registration.route,
                            true
                        )
                    }
                    is RegistrationFeature.News.CloseAuthorization -> {
                        if (!navController.popBackStack(AuthorizationDestinations.LogIn.route, true)) {
                            navController.popBackStack(AuthorizationDestinations.Registration.route, true)
                        }
                    }
                }
            }
        }

        RegistrationScreen(
            state = uiState.value,
            onEmailTextChanged = { email ->
                component.accept(RegistrationFeature.Intent.ChangeEmailText(email))
            },
            onPasswordTextChanged = { password ->
                component.accept(RegistrationFeature.Intent.ChangePasswordText(password))
            },
            onRegisterClicked = {
                component.accept(RegistrationFeature.Intent.PerformRegistration)
            },
            onBackArrowPressed = {
                component.accept(RegistrationFeature.Intent.GoBack)
            }
        )
    }
}