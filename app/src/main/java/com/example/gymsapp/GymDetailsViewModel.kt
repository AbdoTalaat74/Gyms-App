package com.example.gymsapp

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
            withContext(Dispatchers.IO){
                val gym = gymsDao.getGym(id)
                state.value = gym
            }

        }
    }

}