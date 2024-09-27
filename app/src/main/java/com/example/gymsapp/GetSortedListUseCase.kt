package com.example.gymsapp

class GetSortedListUseCase {

    private val repository = GymsRepository()

    suspend operator fun invoke():List<Gym>{
        return repository.getGyms().sortedBy { it.name }
    }

}