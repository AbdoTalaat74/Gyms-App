package com.example.gymsapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsViewModel() : ViewModel() {
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
            state = getAllGyms()
        }
    }

    private suspend fun getAllGyms() = withContext(Dispatchers.IO) {
        try {
            updateLocalDatabase()
        } catch (e: Exception) {
            if (gymsDao.getAll().isEmpty()) {
                throw Exception("Something went wrong, no data was found, try connecting to Internet")
            }
        }
        gymsDao.getAll()
    }



    private suspend fun updateLocalDatabase() {
        val gyms = apiService.getGyms()
        val favoriteGymsList = gymsDao.getFavoriteItems()
        gymsDao.addAll(gyms)
        gymsDao.updateAll(
            favoriteGymsList.map { GymFavoriteState(id=it.id, isFavorite = true) }
        )

    }


    fun toggleFavoriteState(gymId: Int) {
        val gyms = state.toMutableList()
        val itemIndex = gyms.indexOfFirst { it.id == gymId }
        viewModelScope.launch {
            val updatedGymsList = toggleFavoriteGym(gymId, !gyms[itemIndex].isFavorite)
            state = updatedGymsList
        }
    }

    private suspend fun toggleFavoriteGym(gymId: Int, newFavoriteState: Boolean) =
        withContext(Dispatchers.IO) {
            gymsDao.update(
                GymFavoriteState(
                    id = gymId, isFavorite = newFavoriteState
                )
            )
            gymsDao.getAll()
        }


}