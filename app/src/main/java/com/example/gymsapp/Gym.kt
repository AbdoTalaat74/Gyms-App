package com.example.gymsapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector
import com.google.gson.annotations.SerializedName



data class Gym(
    val id:Int,
    @SerializedName("gym_name")
    val name: String,
    @SerializedName("gym_location")
    val location: String,
    val img: ImageVector,
    var isFavorite:Boolean = false
)