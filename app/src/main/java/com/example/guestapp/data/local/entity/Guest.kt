package com.example.guestapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.guestapp.domain.models.Gender
import com.example.guestapp.domain.models.WeddingSide

@Entity("guests")
data class Guest(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val age: Int,
    val gender: Gender,
    val side: WeddingSide,
    val tableId: Long? = null,
    val phoneNumber: String? = null,
    val dietaryRestrictions: String? = null,
    val isConfirmed: Boolean = false,
    val notes: String? = null,
)