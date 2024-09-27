package com.example.gymsapp.gyms.domain

import com.example.gymsapp.gyms.data.GymsRepository

class GetInitialGymsUseCase {
    private val gymsRepository = GymsRepository()
    private val getSortedListUseCase = GetSortedListUseCase()
    suspend operator fun invoke() : List<Gym>{
         gymsRepository.loadGyms()
        return getSortedListUseCase()
    }
}