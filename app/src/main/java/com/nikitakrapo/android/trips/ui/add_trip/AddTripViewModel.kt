package com.nikitakrapo.android.trips.ui.add_trip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikitakrapo.android.trips.data.TripsRepository
import com.nikitakrapo.android.trips.mvi.ViewModelInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class AddTripViewModel @Inject constructor(
    private val tripsRepository: TripsRepository,
) : ViewModel(), ViewModelInteractor<AddTripUiState, AddTripEvent, AddTripAction> {

    override val uiState: StateFlow<AddTripUiState>
        get() = _uiState
    private val _uiState: MutableStateFlow<AddTripUiState> =
        MutableStateFlow(AddTripUiState())

    override val actions: StateFlow<AddTripAction?>
        get() = _actions
    private val _actions: MutableStateFlow<AddTripAction?> = MutableStateFlow(null)

    private val _events: MutableSharedFlow<AddTripEvent> = MutableSharedFlow()

    init {
        // TODO: move dispatchers abstraction
        viewModelScope.launch(Dispatchers.IO) {
            _events.collect(this@AddTripViewModel::onNewEvent)
        }
    }

    fun onViewEvent(event: AddTripEvent) {
        runBlocking { _events.emit(event) }
    }

    private fun onNewEvent(event: AddTripEvent) {
        _uiState.value = reduce(_uiState.value, event)
    }

    override fun reduce(previousState: AddTripUiState, event: AddTripEvent): AddTripUiState {
        return when (event) {
            is AddTripEvent.BackArrowClicked -> {
                _actions.value = AddTripAction.CloseScreen
                previousState
            }
            is AddTripEvent.NameTextFieldChanged -> previousState.copy(nameTextField = event.text)
            is AddTripEvent.AddTripClicked -> {
                addTrip()
                previousState.copy(isAddFabLoading = true)
            }

            is AddTripEvent.TripAdded -> {
                _actions.value = AddTripAction.CloseScreen
                previousState
            }
        }
    }

    private fun addTrip() {
        viewModelScope.launch(Dispatchers.IO) {
            tripsRepository.addTrip(uiState.value.nameTextField)
            _events.emit(AddTripEvent.TripAdded)
        }
    }
}