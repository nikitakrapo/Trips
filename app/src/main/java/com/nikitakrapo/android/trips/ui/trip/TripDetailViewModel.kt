package com.nikitakrapo.android.trips.ui.trip

import androidx.lifecycle.ViewModel
import com.nikitakrapo.android.trips.data.TripsRepository
import com.nikitakrapo.android.trips.mvi.ViewModelInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TripDetailViewModel(
    private val tripsRepository: TripsRepository
) : ViewModel(), ViewModelInteractor<TripDetailUiState, TripDetailEvent, Unit> {
    override val uiState: StateFlow<TripDetailUiState>
        get() = _uiState
    private val _uiState: MutableStateFlow<TripDetailUiState> =
        MutableStateFlow(TripDetailUiState("FILLER"))

    override val actions: StateFlow<Unit>
        = MutableStateFlow(Unit)

    override fun reduce(previousState: TripDetailUiState, event: TripDetailEvent): TripDetailUiState {
        return previousState
    }
}