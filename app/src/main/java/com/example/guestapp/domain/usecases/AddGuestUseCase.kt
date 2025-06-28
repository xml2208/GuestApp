package com.example.guestapp.domain.usecases

import com.example.guestapp.data.local.entity.Guest
import com.example.guestapp.domain.repository.GuestRepository

class AddGuestUseCase(private val repository: GuestRepository) {
    suspend operator fun invoke(guest: Guest): Result<Long> = try {
        val id = repository.addGuest(guest)
        Result.success(id)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
