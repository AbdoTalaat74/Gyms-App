package com.example.gymsapp.gyms.data

import com.example.gymsapp.gyms.data.local.GymsDAO
import com.example.gymsapp.gyms.data.local.LocalGym
import com.example.gymsapp.gyms.data.local.LocalGymFavoriteState
import com.example.gymsapp.gyms.data.remote.GymApiService
import com.example.gymsapp.gyms.domain.Gym
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GymsRepository @Inject constructor(
    private val apiService :GymApiService,
    private val gymsDao:GymsDAO
) {

    suspend fun toggleFavoriteGym(gymId: Int, favoriteState: Boolean) =
        withContext(Dispatchers.IO) {
            gymsDao.update(
                LocalGymFavoriteState(
                    id = gymId, isFavorite = favoriteState
                )
            )
            gymsDao.getAll()
        }

    suspend fun loadGyms() = withContext(Dispatchers.IO) {
        try {
            updateLocalDatabase()
        } catch (e: Exception) {
            if (gymsDao.getAll().isEmpty()) {
                throw Exception("Something went wrong, no data was found, try connecting to Internet")
            }
        }

    }

    suspend fun getGyms():List<Gym> {
        return withContext(Dispatchers.IO){
            return@withContext gymsDao.getAll().map {
                Gym(it.id,it.name,it.location,it.isOpen,it.isFavorite)
            }
        }
    }


    private suspend fun updateLocalDatabase() {
        val gyms = apiService.getGyms()
        val favoriteGymsList = gymsDao.getFavoriteItems()
        gymsDao.addAll(gyms.map {
            LocalGym(it.id,it.name,it.location,it.isOpen)
        })
        gymsDao.updateAll(
            favoriteGymsList.map { LocalGymFavoriteState(id = it.id, isFavorite = true) }
        )

    }
}