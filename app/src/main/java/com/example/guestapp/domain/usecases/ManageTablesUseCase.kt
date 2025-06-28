package com.example.guestapp.domain.usecases

import com.example.guestapp.data.local.entity.TableWithGuests
import com.example.guestapp.data.local.entity.WeddingTable
import com.example.guestapp.domain.repository.GuestRepository
import kotlinx.coroutines.flow.Flow

class ManageTablesUseCase(private val repository: GuestRepository) {
    fun getTablesWithGuests(): Flow<List<TableWithGuests>> = repository.getTablesWithGuests()
    
    suspend fun addTable(tableNumber: Int, tableName: String, capacity: Int): Result<Long> = try {
        val table = WeddingTable(
            tableNumber = tableNumber,
            tableName = tableName,
            capacity = capacity
        )
        val id = repository.addTable(table)
        Result.success(id)
    } catch (e: Exception) {
        Result.failure(e)
    }
}