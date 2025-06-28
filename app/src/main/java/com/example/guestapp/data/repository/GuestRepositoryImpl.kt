package com.example.guestapp.data.repository

import com.example.guestapp.data.local.dao.GuestDao
import com.example.guestapp.data.local.dao.TableDao
import com.example.guestapp.data.local.entity.Guest
import com.example.guestapp.data.local.entity.TableWithGuests
import com.example.guestapp.data.local.entity.WeddingTable
import com.example.guestapp.domain.models.GuestStats
import com.example.guestapp.domain.repository.GuestRepository
import kotlinx.coroutines.flow.Flow

class GuestRepositoryImpl(
    private val guestDao: GuestDao,
    private val tableDao: TableDao,
) : GuestRepository {

    override fun getAllGuests(): Flow<List<Guest>> = guestDao.getAllGuests()

    override fun getAllTables(): Flow<List<WeddingTable>> = tableDao.getAllTables()

    override fun getTablesWithGuests(): Flow<List<TableWithGuests>> = tableDao.getTablesWithGuests()

    override fun getGuestStats(): Flow<GuestStats> = tableDao.getGuestStats()

    override fun searchGuests(query: String): Flow<List<Guest>> = guestDao.searchGuests(query)

    override suspend fun addGuest(guest: Guest): Long = guestDao.insertGuest(guest)

    override suspend fun deleteGuest(guest: Guest) = guestDao.deleteGuest(guest)

    override suspend fun addTable(table: WeddingTable): Long = tableDao.insertTable(table)

    override suspend fun updateTable(table: WeddingTable) = tableDao.updateTable(table)

    override suspend fun deleteTable(tableId: Long) {
        guestDao.deleteGuestsByTable(tableId)
        tableDao.deactivateTable(tableId)
    }
}
