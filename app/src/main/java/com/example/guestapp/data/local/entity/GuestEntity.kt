package com.example.guestapp.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

data class TableWithGuests(
    @Embedded val table: WeddingTable,
    @Relation(
        parentColumn = "id",
        entityColumn = "tableId"
    )
    val guests: List<Guest>
)
