package com.example.gymsapp.gyms.presentation.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymsapp.GymsApplication
import com.example.gymsapp.gyms.data.local.GymsDatabase
import com.example.gymsapp.gyms.domain.Gym
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GymDetailsViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {


    var state = mutableStateOf<Gym?>(null)
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    private val gymsDao = GymsDatabase.getDaoInstance(GymsApplication.getApplicationContext())


    init {

        val gymId = savedStateHandle.get<Int>("gym_id") ?: 0
        getGym(gymId)
    }


    private fun getGym(id: Int) {
        viewModelScope.launch(coroutineExceptionHandler) {
            withContext(Dispatchers.IO) {
                val gym = gymsDao.getGym(id)
                state.value = Gym(
                    gym.id,
                    gym.name,
                    gym.location,
                    gym.isOpen,
                    gym.isFavorite
                )
            }

        }
    }

}