package com.example.guestapp.presentation.add_guest

import com.example.guestapp.domain.models.Gender
import com.example.guestapp.domain.models.WeddingSide

data class AddGuestUiState(
    val name: String,
    val age: String,
    val gender: Gender,
    val side: WeddingSide,
    val phoneNumber: String,
    val selectedTableId: Long?,
    val dietaryRestrictions: String,
    val notes: String,
    val isLoading: Boolean,
    val showSuccessDialog: Boolean,
    val errorMessage: String?,
    val expandedTable: Boolean,
) {
    companion object {
        val INITIAL =
            AddGuestUiState(
                name = "",
                age = "",
                gender = Gender.MALE,
                side = WeddingSide.BRIDE,
                phoneNumber = "",
                selectedTableId = null,
                dietaryRestrictions = "",
                notes = "",
                isLoading = false,
                showSuccessDialog = false,
                errorMessage = null,
                expandedTable = false,
            )
    }
}
