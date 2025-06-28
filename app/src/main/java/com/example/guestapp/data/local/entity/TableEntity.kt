package com.example.guestapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wedding_tables")
data class WeddingTable(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val tableNumber: Int,
    val tableName: String,
    val capacity: Int,
    val isActive: Boolean = true,
)
