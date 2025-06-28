package com.example.guestapp.presentation.guests_list

enum class FilterType {
    ALL,
    CONFIRMED,
    PENDING,
    BRIDE,
    GROOM;
}

fun FilterType.displayName(): String =
    when (this) {
        FilterType.ALL -> "Все"
        FilterType.CONFIRMED -> "Подтвердили"
        FilterType.PENDING -> "Ожидают"
        FilterType.BRIDE -> "Невеста"
        FilterType.GROOM -> "Жених"
    }