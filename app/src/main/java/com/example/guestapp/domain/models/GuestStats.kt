package com.example.guestapp.domain.models

data class GuestStats(
    val totalGuests: Int,
    val confirmedGuests: Int,
    val brideGuests: Int,
    val groomGuests: Int,
    val tablesOccupied: Int,
    val totalCapacity: Int
) {
    companion object {
        val Initial = GuestStats(
            totalGuests = 0,
            confirmedGuests = 0,
            brideGuests = 0,
            groomGuests = 0,
            tablesOccupied = 0,
            totalCapacity = 0
        )
    }
}