package com.example.gymsapp.gyms.domain

import com.example.gymsapp.gyms.data.GymsRepository
import javax.inject.Inject

class GetInitialGymsUseCase @Inject constructor(
    private val gymsRepository: GymsRepository,
    private val getSortedListUseCase: GetSortedListUseCase
) {

    suspend operator fun invoke(): List<Gym> {
        gymsRepository.loadGyms()
        return getSortedListUseCase()
    }
}