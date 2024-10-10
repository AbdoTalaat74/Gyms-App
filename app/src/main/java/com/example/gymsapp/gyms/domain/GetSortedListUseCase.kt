package com.example.gymsapp.gyms.domain

import com.example.gymsapp.gyms.data.GymsRepository
import javax.inject.Inject

class GetSortedListUseCase @Inject constructor(private val repository: GymsRepository) {


    suspend operator fun invoke(): List<Gym> {
        return repository.getGyms().sortedBy { it.name }
    }

}