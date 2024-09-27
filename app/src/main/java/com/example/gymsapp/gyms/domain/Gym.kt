package com.example.gymsapp.gyms.domain


data class Gym(
    val id:Int,
    val name: String,
    val location: String,
    val isOpen: Boolean = false,
    val isFavorite:Boolean = false
)