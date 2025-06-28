package com.example.guestapp.domain.usecases

import com.example.guestapp.domain.repository.GuestRepository

class GetTablesUseCase(private val repository: GuestRepository) {
    suspend operator fun invoke() = repository.getAllTables()
}