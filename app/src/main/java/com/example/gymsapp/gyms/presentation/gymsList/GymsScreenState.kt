package com.example.gymsapp.gyms.presentation.gymsList

import com.example.gymsapp.gyms.domain.Gym

data class GymsScreenState(
    val gyms:List<Gym>,
    val isLoading:Boolean,
    val error:String? = null
)
