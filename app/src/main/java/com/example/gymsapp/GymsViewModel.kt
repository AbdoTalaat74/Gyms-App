package com.example.gymsapp

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsViewModel(private val stateHandle: SavedStateHandle) : ViewModel() {
    private val apiService: GymsApiService
    private lateinit var gymsCall: Call<List<Gym>>

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl(
                "https://cairo-gyms-b91c2-default-rtdb.firebaseio.com/"
            )
            .build()

        apiService = retrofit.create(GymsApiService::class.java)

        getGyms()
    }

    private fun getGyms() {
        gymsCall = apiService.getGyms()
        gymsCall.enqueue(object : Callback<List<Gym>> {
            override fun onResponse(call: Call<List<Gym>>, response: Response<List<Gym>>) {
                response.body()?.let {
                    state = it.restoreSelectedGyms()
                }
            }

            override fun onFailure(call: Call<List<Gym>>, t: Throwable) {
                t.printStackTrace()
            }

        })

    }

    override fun onCleared() {
        super.onCleared()
        gymsCall.cancel()
    }

    var state by mutableStateOf(emptyList<Gym>())
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
//        val gyms = getGyms()
        stateHandle.get<List<Int>?>(FAV_IDS)?.let { savedIds ->
            savedIds.forEach { gymId ->
                this.find { it.id == gymId }?.isFavorite = true
            }


        }
        return this
    }

    companion object {
        const val FAV_IDS = "favoriteGymsIDs"
    }
}