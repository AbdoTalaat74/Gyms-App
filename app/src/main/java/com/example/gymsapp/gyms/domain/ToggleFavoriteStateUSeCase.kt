package com.example.gymsapp.gyms.domain

import com.example.gymsapp.gyms.data.GymsRepository
import javax.inject.Inject

class ToggleFavoriteStateUSeCase @Inject constructor(
    private val gymsRepository: GymsRepository,
    private val getSortedListUseCase: GetSortedListUseCase
) {


    suspend operator fun invoke(id: Int, odlState: Boolean): List<Gym> {
        val newState = odlState.not()
        gymsRepository.toggleFavoriteGym(id, newState)
        return getSortedListUseCase()
    }
}