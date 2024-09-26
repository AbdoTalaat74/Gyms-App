package com.example.gymsapp

import androidx.room.ColumnInfo

data class GymFavoriteState(
    @ColumnInfo(name = "gym_id")
    val id: Int,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false
)
