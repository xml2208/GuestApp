package com.example.guestapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.guestapp.data.local.entity.Guest
import kotlinx.coroutines.flow.Flow

@Dao
interface GuestDao {
    @Query("SELECT * FROM guests ORDER BY name ASC")
    fun getAllGuests(): Flow<List<Guest>>

    @Query("SELECT * FROM guests WHERE tableId = :tableId")
    fun getGuestsByTable(tableId: Long): Flow<List<Guest>>

    @Query("SELECT * FROM guests WHERE name LIKE '%' || :query || '%' OR phoneNumber LIKE '%' || :query || '%'")
    fun searchGuests(query: String): Flow<List<Guest>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGuest(guest: Guest): Long

    @Delete
    suspend fun deleteGuest(guest: Guest)

    @Query("DELETE FROM guests WHERE tableId = :tableId")
    suspend fun deleteGuestsByTable(tableId: Long)
}
