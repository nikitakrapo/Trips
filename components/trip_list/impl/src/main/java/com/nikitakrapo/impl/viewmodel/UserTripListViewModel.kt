package com.nikitakrapo.impl.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikitakrapo.impl.data.TripListRepositoryImpl
import com.nikitakrapo.trip_list.component.TripList
import com.nikitakrapo.trip_list.component.TripListComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserTripListViewModel @Inject constructor(
    tripListRepository: TripListRepositoryImpl,
): ViewModel() {
    val component: TripList = TripListComponent(
        repository = tripListRepository,
        output = {
            viewModelScope.launch {
                _output.emit(it)
            }
        }
    )

    private val _output: MutableSharedFlow<TripList.Output> = MutableSharedFlow()
    val output: SharedFlow<TripList.Output> = _output.asSharedFlow()
}