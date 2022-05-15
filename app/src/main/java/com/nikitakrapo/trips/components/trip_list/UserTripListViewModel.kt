package com.nikitakrapo.trips.components.trip_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikitakrapo.trips.data.TripsRepository
import com.nikitakrapo.trips.data.dto.Trip
import com.nikitakrapo.trips.mvi.ViewModelInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserTripListViewModel @Inject constructor(
    private val tripsRepository: TripsRepository
) : ViewModel(), ViewModelInteractor<UserTripListUiState, UserTripListEvent, Unit> {
    override val uiState: StateFlow<UserTripListUiState>
        get() = _uiState
    private val _uiState: MutableStateFlow<UserTripListUiState> =
        MutableStateFlow(UserTripListUiState())

    override val actions: StateFlow<Unit> = MutableStateFlow(Unit)

    private val _events: MutableSharedFlow<UserTripListEvent> = MutableSharedFlow()

    init {
        // TODO: move dispatchers abstraction
        viewModelScope.launch(Dispatchers.IO) {
            _events.collect(this@UserTripListViewModel::onNewEvent)
        }

        viewModelScope.launch(Dispatchers.IO) {
            tripsRepository.getTripsFlow().collect {
                _events.emit(UserTripListEvent.LoadedFromCache(it))
            }
        }
    }

    fun onViewEvent(event: UserTripListEvent) {
        // TODO: move dispatchers abstraction
        viewModelScope.launch(Dispatchers.Main) {
            _events.emit(event)
        }
    }

    private fun onNewEvent(event: UserTripListEvent) {
        _uiState.value = reduce(_uiState.value, event)
    }

    override fun reduce(
        previousState: UserTripListUiState,
        event: UserTripListEvent
    ): UserTripListUiState {
        return when (event) {
            is UserTripListEvent.LoadedFromCache -> {
                previousState.copy(
                    showProgressBar = false,
                    swipeRefreshRefreshing = false,
                    tripList = event.tripList
                )
            }
            is UserTripListEvent.LoadedFromNetwork -> {
                previousState.copy(
                    showProgressBar = false,
                    swipeRefreshRefreshing = false,
                    tripList = event.tripList
                )
            }
            is UserTripListEvent.SwipeRefresh -> {
                fetchTrips()
                previousState.copy(
                    swipeRefreshRefreshing = true
                )
            }
            is UserTripListEvent.LongTripClick -> {
                removeTrip(event.trip)
                previousState
            }
        }
    }

    private fun fetchTrips() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            _events.emit(UserTripListEvent.LoadedFromCache(uiState.value.tripList))
        }
    }

    private fun removeTrip(trip: Trip) {
        viewModelScope.launch(Dispatchers.IO) {
            tripsRepository.removeTrip(trip.name)
        }
    }
}