package com.nikitakrapo.android.trips.ui.home.trips

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.random.Random

class UserTripListViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<UserTripListUiState> = MutableStateFlow(UserTripListUiState())
    val uiState: StateFlow<UserTripListUiState>
        get() = _uiState

    private val _events: MutableSharedFlow<UserTripListEvent> = MutableSharedFlow()

    init {
        // TODO: move dispatchers abstraction
        viewModelScope.launch(Dispatchers.IO) {
            _events.collect(this@UserTripListViewModel::onNewEvent)
        }

        prefetchTrips()
    }

    private fun prefetchTrips() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            _events.emit(
                UserTripListEvent.LoadedFromNetwork(
                    listOf(

                    )
                )
            )
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

    private fun reduce(state: UserTripListUiState, event: UserTripListEvent): UserTripListUiState {
        return when (event) {
            is UserTripListEvent.LoadedFromNetwork -> {
                state.copy(
                    showProgressBar = false,
                    swipeRefreshRefreshing = false,
                    tripList = event.tripList
                )
            }
            UserTripListEvent.SwipeRefresh -> {
                onSwipeRefresh()
                state.copy(
                    swipeRefreshRefreshing = true
                )
            }
        }
    }

    private fun onSwipeRefresh() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(500)
            _events.emit(
                UserTripListEvent.LoadedFromNetwork(
                    listOf(
                        Trip(name = "Bebrik1", dates = "Datik"),
                        Trip(name = "Bebrik2", dates = "Datik"),
                        Trip(name = "Bebrik3", dates = "Datik"),
                        Trip(name = "Bebrik4", dates = "Datik"),
                        Trip(name = "Bebrik5", dates = "Datik"),
                        Trip(name = "Bebrik6", dates = "Datik"),
                        Trip(name = "Bebrik7", dates = "Datik"),
                        Trip(name = "Bebrik8", dates = "Datik"),
                        Trip(name = "Bebrik9", dates = "Datik"),
                        Trip(name = "Bebrik10", dates = "Datik"),
                        Trip(name = "Bebrik11", dates = "Datik"),
                        Trip(name = "Bebrik12", dates = "Datik"),
                    ).take(
                        if (Random.nextBoolean()) {
                            Int.MAX_VALUE
                        } else {
                            0
                        }
                    )
                )
            )
        }
    }
}