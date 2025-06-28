package com.example.guestapp.presentation.add_guest

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guestapp.data.local.entity.Guest
import com.example.guestapp.domain.models.Gender
import com.example.guestapp.domain.models.WeddingSide
import com.example.guestapp.domain.repository.GuestRepository
import com.example.guestapp.domain.usecases.AddGuestUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddGuestViewModel(
    private val addGuestUseCase: AddGuestUseCase,
    repository: GuestRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddGuestUiState.INITIAL)
    val uiState = _uiState.asStateFlow()

    val tables = repository.getAllTables()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun updateAge(age: String) {
        _uiState.value = _uiState.value.copy(age = age)
    }

    fun updateGender(gender: Gender) {
        _uiState.value = _uiState.value.copy(gender = gender)
    }

    fun updateSide(side: WeddingSide) {
        _uiState.value = _uiState.value.copy(side = side)
    }

    fun updatePhone(phone: String) {
        _uiState.value = _uiState.value.copy(phoneNumber = phone)
    }

    fun updateTable(tableId: Long?) {
        _uiState.value = _uiState.value.copy(selectedTableId = tableId, expandedTable = false)
    }

    fun updateDietaryRestrictions(restrictions: String) {
        _uiState.value = _uiState.value.copy(dietaryRestrictions = restrictions)
    }

    fun updateNotes(notes: String) {
        _uiState.value = _uiState.value.copy(notes = notes)
    }

    fun onTableExpandedChange() {
        _uiState.update { state -> state.copy(expandedTable = !state.expandedTable) }
    }

    fun onDismissTableChoose() {
        _uiState.update { state -> state.copy(expandedTable = false) }
    }

    fun addGuest() {
        val state = _uiState.value

        if (state.name.isBlank()) {
            onError("Имя гостя обязательно для заполнения")
            return
        }

        val ageInt = state.age.toIntOrNull()
        if (ageInt == null || ageInt <= 0) {
            onError("Введите корректный возраст")
            return
        }

        val guest = Guest(
            name = state.name.trim(),
            age = ageInt,
            gender = state.gender,
            side = state.side,
            tableId = state.selectedTableId,
            phoneNumber = state.phoneNumber.takeIf { it.isNotBlank() },
            dietaryRestrictions = state.dietaryRestrictions.takeIf { it.isNotBlank() },
            notes = state.notes.takeIf { it.isNotBlank() }
        )

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            addGuestUseCase(guest)
                .onSuccess {
                    _uiState.value = AddGuestUiState.INITIAL
                    onSuccess()
                }
                .onFailure {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    onError(it.message ?: "Произошла ошибка при добавлении гостя")
                }
        }
    }

    private fun onSuccess() {
        _uiState.update { state -> state.copy(showSuccessDialog = true) }
    }

    private fun onError(errorMessage: String) {
        _uiState.update { state -> state.copy(errorMessage = errorMessage) }
    }

    fun dismissDialog(onBack: () -> Unit) {
        _uiState.update { state -> state.copy(showSuccessDialog = false) }
        onBack()
    }

    fun dismissErrorDialog() {
        _uiState.update { state -> state.copy(errorMessage = null) }
    }
}
