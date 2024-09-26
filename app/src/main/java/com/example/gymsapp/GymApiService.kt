package com.example.gymsapp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GymApiService {
    @GET("gyms.json")
    suspend fun getGyms(): List<Gym>

    @GET("gyms.json?orderBy=\"id\"")
    suspend fun getGym(
        @Query("equalTo") id: Int
    ): Response<Map<String, Gym>>
}