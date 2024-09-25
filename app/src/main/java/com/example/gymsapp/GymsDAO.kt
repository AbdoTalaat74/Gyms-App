package com.example.gymsapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface GymsDAO {

     @Query("SELECT * FROM gyms")
    suspend fun getAll():List<Gym>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(gyms:List<Gym>)

    @Update(entity = Gym::class)
    suspend fun update(gymFavoriteState: GymFavoriteState)
}