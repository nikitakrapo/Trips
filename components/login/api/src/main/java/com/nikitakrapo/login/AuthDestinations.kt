package com.nikitakrapo.login

sealed class AuthDestinations(val route: String) {
    object LogIn : AuthDestinations("login")
    object Registration : AuthDestinations("registration")
}