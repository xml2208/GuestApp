package com.example.guestapp.data.converters

import androidx.room.TypeConverter
import com.example.guestapp.domain.models.Gender
import com.example.guestapp.domain.models.WeddingSide

class Converters {
    @TypeConverter
    fun fromGender(gender: Gender): String = gender.name
    
    @TypeConverter
    fun toGender(gender: String): Gender = Gender.valueOf(gender)
    
    @TypeConverter
    fun fromWeddingSide(side: WeddingSide): String = side.name
    
    @TypeConverter
    fun toWeddingSide(side: String): WeddingSide = WeddingSide.valueOf(side)
}