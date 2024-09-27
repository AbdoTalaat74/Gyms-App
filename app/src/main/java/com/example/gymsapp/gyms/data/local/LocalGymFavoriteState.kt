package com.example.gymsapp.gyms.data.local

import androidx.room.ColumnInfo

data class LocalGymFavoriteState(
    @ColumnInfo(name = "gym_id")
    val id: Int,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false
)
