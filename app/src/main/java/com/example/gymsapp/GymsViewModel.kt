package com.example.gymsapp

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsViewModel(private val stateHandle: SavedStateHandle) : ViewModel() {
    var state by mutableStateOf(emptyList<Gym>())
    private var apiService: GymApiService
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }
    private val gymsDao = GymsDatabase.getDaoInstance(GymsApplication.getApplicationContext())

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl("https://cairo-gyms-b91c2-default-rtdb.firebaseio.com/")
            .build()
        apiService = retrofit.create(GymApiService::class.java)
        getGyms()
    }

    private fun getGyms() {
        viewModelScope.launch(coroutineExceptionHandler) {
            val gymsResponse = getGymsFromRemote()
            state = gymsResponse.restoreSelectedGyms()

        }
    }

    private suspend fun getGymsFromRemote() = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getGyms()
            if (response.isSuccessful) {
                response.body()?.let { gyms ->
                    gymsDao.addAll(gyms)
                    return@withContext gyms
                }
            }
            Log.e("GymsViewModel", "Response is not successful or empty body")
            return@withContext emptyList<Gym>()
        } catch (e: Exception) {
            Log.e("GymsViewModel", "Error fetching gyms", e)
            return@withContext emptyList<Gym>()
        }
    }

    fun toggleFavoriteState(gymId: Int) {
        val gyms = state.toMutableList()
        val itemIndex = gyms.indexOfFirst { it.id == gymId }
        gyms[itemIndex] = gyms[itemIndex].copy(isFavorite = !gyms[itemIndex].isFavorite)
        storeSelectedGym(gyms[itemIndex])
        state = gyms
    }

    private fun storeSelectedGym(gym: Gym) {
        val savedHandleList = stateHandle.get<List<Int>?>(FAV_IDS).orEmpty().toMutableList()
        if (gym.isFavorite) savedHandleList.add(gym.id) else savedHandleList.remove(gym.id)
        stateHandle[FAV_IDS] = savedHandleList
        Log.e("storedFavoriteGymsLog", savedHandleList.toString())

    }

    private fun List<Gym>.restoreSelectedGyms(): List<Gym> {
        stateHandle.get<List<Int>?>(FAV_IDS)?.let { savedIds ->
            savedIds.forEach { gymId ->
                this.find { it.id == gymId }?.isFavorite = true
            }


        }
        Log.e("favoriteGymsLog", this.toString())
        return this
    }

    companion object {
        const val FAV_IDS = "favoriteGymsIDs"
    }
}