package com.example.guestapp.domain.usecases

import com.example.guestapp.domain.models.GuestStats
import com.example.guestapp.domain.repository.GuestRepository
import kotlinx.coroutines.flow.Flow

class GetGuestStatsUseCase(private val repository: GuestRepository) {
    operator fun invoke(): Flow<GuestStats> = repository.getGuestStats()
}
