package com.nikitakrapo.android.trips.ui.add_trip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AddTripViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<AddTripUiState> = MutableStateFlow(AddTripUiState())
    val uiState: StateFlow<AddTripUiState>
        get() = _uiState

    private val _events: MutableSharedFlow<AddTripEvent> = MutableSharedFlow()

    init {
        // TODO: move dispatchers abstraction
        viewModelScope.launch(Dispatchers.IO) {
            _events.collect(this@AddTripViewModel::onNewEvent)
        }
    }

    fun onViewEvent(event: AddTripEvent) {
        // TODO: move dispatchers abstraction
        viewModelScope.launch(Dispatchers.Main) {
            _events.emit(event)
        }
    }

    private fun onNewEvent(event: AddTripEvent) {
        _uiState.value = reduce(_uiState.value, event)
    }

    private fun reduce(state: AddTripUiState, event: AddTripEvent): AddTripUiState {
        return when (event) {
            is AddTripEvent.NameTextFieldChanged -> state.copy(name = event.text)
        }
    }
}