package com.example.gymsapp

class ToggleFavoriteStateUSeCase {
    private val gymsRepository = GymsRepository()
    private val getSortedListUseCase = GetSortedListUseCase()

    suspend operator fun invoke(id: Int, odlState: Boolean):List<Gym> {
        val newState = odlState.not()
        gymsRepository.toggleFavoriteGym(id,newState)
        return getSortedListUseCase()
    }
}