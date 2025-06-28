package com.example.guestapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.guestapp.data.converters.Converters
import com.example.guestapp.data.local.dao.GuestDao
import com.example.guestapp.data.local.dao.TableDao
import com.example.guestapp.data.local.entity.Guest
import com.example.guestapp.data.local.entity.WeddingTable

@Database(
    entities = [Guest::class, WeddingTable::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class WeddingDatabase : RoomDatabase() {
    abstract fun guestDao(): GuestDao
    abstract fun tableDao(): TableDao
}
