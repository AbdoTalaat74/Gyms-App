package com.example.gymsapp

data class GymsScreenState(
    val gyms:List<Gym>,
    val isLoading:Boolean,
    val error:String? = null
)
