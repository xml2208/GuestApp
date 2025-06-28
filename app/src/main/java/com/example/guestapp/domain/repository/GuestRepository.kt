package com.example.guestapp.domain.repository

import com.example.guestapp.domain.models.GuestStats
import com.example.guestapp.data.local.entity.Guest
import com.example.guestapp.data.local.entity.TableWithGuests
import com.example.guestapp.data.local.entity.WeddingTable
import kotlinx.coroutines.flow.Flow

interface GuestRepository {

    fun getAllGuests(): Flow<List<Guest>>

     fun getAllTables(): Flow<List<WeddingTable>>

    fun getTablesWithGuests(): Flow<List<TableWithGuests>>

    fun getGuestStats(): Flow<GuestStats>

    fun searchGuests(query: String): Flow<List<Guest>>

    suspend fun addGuest(guest: Guest): Long

    suspend fun deleteGuest(guest: Guest)

    suspend fun addTable(table: WeddingTable): Long

    suspend fun updateTable(table: WeddingTable)

    suspend fun deleteTable(tableId: Long)
}
