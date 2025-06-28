package com.example.guestapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.guestapp.domain.models.GuestStats
import com.example.guestapp.data.local.entity.TableWithGuests
import com.example.guestapp.data.local.entity.WeddingTable
import kotlinx.coroutines.flow.Flow

@Dao
interface TableDao {
    @Query("SELECT * FROM wedding_tables WHERE isActive = 1 ORDER BY tableNumber ASC")
    fun getAllTables(): Flow<List<WeddingTable>>
    
    @Transaction
    @Query("SELECT * FROM wedding_tables WHERE isActive = 1 ORDER BY tableNumber ASC")
    fun getTablesWithGuests(): Flow<List<TableWithGuests>>
    
    @Query("SELECT * FROM wedding_tables WHERE id = :id")
    suspend fun getTableById(id: Long): WeddingTable?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTable(table: WeddingTable): Long
    
    @Update
    suspend fun updateTable(table: WeddingTable)
    
    @Query("UPDATE wedding_tables SET isActive = 0 WHERE id = :id")
    suspend fun deactivateTable(id: Long)
    
    @Query("""
        SELECT 
            COUNT(*) as totalGuests,
            SUM(CASE WHEN isConfirmed = 1 THEN 1 ELSE 0 END) as confirmedGuests,
            SUM(CASE WHEN side = 'BRIDE' THEN 1 ELSE 0 END) as brideGuests,
            SUM(CASE WHEN side = 'GROOM' THEN 1 ELSE 0 END) as groomGuests,
            COUNT(DISTINCT tableId) as tablesOccupied,
            COALESCE((SELECT SUM(capacity) FROM wedding_tables WHERE isActive = 1), 0) as totalCapacity
        FROM guests
    """)

    fun getGuestStats(): Flow<GuestStats>
}
