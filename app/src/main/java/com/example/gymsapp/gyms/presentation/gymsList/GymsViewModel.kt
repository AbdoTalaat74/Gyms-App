package com.example.gymsapp.gyms.presentation.gymsList

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymsapp.gyms.domain.GetInitialGymsUseCase
import com.example.gymsapp.gyms.domain.ToggleFavoriteStateUSeCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GymsViewModel @Inject constructor(
    private val getInitialGymsUseCase: GetInitialGymsUseCase,
    private val toggleFavoriteStateUSeCase: ToggleFavoriteStateUSeCase

) : ViewModel() {
    private var _state by mutableStateOf(
        GymsScreenState(
            gyms = emptyList(), isLoading = true
        )
    )
    val state: State<GymsScreenState>
        get() = derivedStateOf { _state }
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        _state = _state.copy(isLoading = false, error = throwable.message)
    }

    init {

        getGyms()
    }

    private fun getGyms() {
        viewModelScope.launch(coroutineExceptionHandler) {
            val receivedGyms = getInitialGymsUseCase()
            _state = _state.copy(
                gyms = receivedGyms,
                isLoading = false
            )
        }
    }


    fun toggleFavoriteState(gymId: Int, oldValue: Boolean) {
        viewModelScope.launch {
            val updatedGymsList = toggleFavoriteStateUSeCase(gymId, oldValue)
            _state = _state.copy(gyms = updatedGymsList)
        }
    }


}