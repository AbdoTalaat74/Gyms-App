package com.example.gymsapp.gyms.data

import com.example.gymsapp.GymsApplication
import com.example.gymsapp.gyms.data.local.GymsDatabase
import com.example.gymsapp.gyms.data.local.LocalGym
import com.example.gymsapp.gyms.data.local.LocalGymFavoriteState
import com.example.gymsapp.gyms.data.remote.GymApiService
import com.example.gymsapp.gyms.domain.Gym
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsRepository {

    private val apiService = Retrofit.Builder()
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .baseUrl("https://cairo-gyms-b91c2-default-rtdb.firebaseio.com/")
        .build()
        .create(GymApiService::class.java)

    private val gymsDao = GymsDatabase.getDaoInstance(GymsApplication.getApplicationContext())


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