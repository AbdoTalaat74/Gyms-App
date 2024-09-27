package com.example.gymsapp.gyms.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface GymsDAO {

    @Query("SELECT * FROM gyms")
    suspend fun getAll(): List<LocalGym>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(gyms: List<LocalGym>)

    @Update(entity = LocalGym::class)
    suspend fun update(gymFavoriteState: LocalGymFavoriteState)

    @Query("SELECT * FROM gyms WHERE is_favorite = 1")
    suspend fun getFavoriteItems(): List<LocalGym>

    @Update(entity = LocalGym::class)
    suspend fun updateAll(gymsState: List<LocalGymFavoriteState>)

    @Query("SELECT * FROM gyms WHERE gym_id = :id")
    suspend fun getGym(id:Int): LocalGym


}