package com.example.guestapp.domain.usecases

import com.example.guestapp.data.local.entity.Guest
import com.example.guestapp.domain.repository.GuestRepository
import kotlinx.coroutines.flow.Flow

class SearchGuestsUseCase(private val repository: GuestRepository) {
    operator fun invoke(query: String): Flow<List<Guest>> = repository.searchGuests(query)
}