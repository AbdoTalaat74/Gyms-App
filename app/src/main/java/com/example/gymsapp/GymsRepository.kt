package com.example.gymsapp

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


     suspend fun toggleFavoriteGym(gymId: Int, newFavoriteState: Boolean) =
        withContext(Dispatchers.IO) {
            gymsDao.update(
                GymFavoriteState(
                    id = gymId, isFavorite = newFavoriteState
                )
            )
            gymsDao.getAll()
        }

     suspend fun getAllGyms() = withContext(Dispatchers.IO,) {
        try {
            updateLocalDatabase()
        } catch (e: Exception) {
            if (gymsDao.getAll().isEmpty()) {
                throw Exception("Something went wrong, no data was found, try connecting to Internet")
            }
        }
        gymsDao.getAll()
    }


     suspend fun updateLocalDatabase() {
        val gyms = apiService.getGyms()
        val favoriteGymsList = gymsDao.getFavoriteItems()
        gymsDao.addAll(gyms)
        gymsDao.updateAll(
            favoriteGymsList.map { GymFavoriteState(id = it.id, isFavorite = true) }
        )

    }
}