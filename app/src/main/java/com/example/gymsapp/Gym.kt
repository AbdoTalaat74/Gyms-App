package com.example.gymsapp

import androidx.compose.ui.graphics.vector.ImageVector
import com.google.gson.annotations.SerializedName


data class Gym(

    val id:Int,
    @SerializedName("gym_name")
    val name: String,
    @SerializedName("gym_location")
    val location: String,
    @SerializedName("is_open")
    val isOpen: Boolean = false,
    var isFavorite:Boolean = false
)