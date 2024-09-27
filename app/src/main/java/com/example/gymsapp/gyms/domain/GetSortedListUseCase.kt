package com.example.gymsapp.gyms.domain

import com.example.gymsapp.gyms.data.GymsRepository

class GetSortedListUseCase {

    private val repository = GymsRepository()

    suspend operator fun invoke():List<Gym>{
        return repository.getGyms().sortedBy { it.name }
    }

}