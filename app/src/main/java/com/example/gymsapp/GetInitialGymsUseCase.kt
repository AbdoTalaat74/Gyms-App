package com.example.gymsapp

class GetInitialGymsUseCase {
    private val gymsRepository = GymsRepository()
    private val getSortedListUseCase = GetSortedListUseCase()
    suspend operator fun invoke() : List<Gym>{
         gymsRepository.loadGyms()
        return getSortedListUseCase()
    }
}