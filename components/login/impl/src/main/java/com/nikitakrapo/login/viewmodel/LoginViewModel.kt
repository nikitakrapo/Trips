package com.nikitakrapo.login.viewmodel

import androidx.lifecycle.ViewModel
import com.nikitakrapo.login.MainLoginFeature
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    val component = MainLoginFeature()

    override fun onCleared() {
        component.dispose()
    }
}