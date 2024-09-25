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


    private var apiService: GymApiService
    var state = mutableStateOf<Gym?>(null)
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    init {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://cairo-gyms-b91c2-default-rtdb.firebaseio.com/")
            .build()

        apiService = retrofit.create(GymApiService::class.java)
        val gymId = savedStateHandle.get<Int>("gym_id") ?: 0
        getGym(gymId)
    }


    private fun getGym(id: Int) {
        viewModelScope.launch(coroutineExceptionHandler) {
            val response = getGymResponse(id)
            if (response.isSuccessful) {
                val gym = response.body()?.values?.first()
                state.value = gym
            } else {
                Log.e("GymDetailsViewModelFailed", response.message())
            }
        }
    }


    private suspend fun getGymResponse(id: Int) =
        withContext(Dispatchers.IO) { apiService.getGym(id) }

}