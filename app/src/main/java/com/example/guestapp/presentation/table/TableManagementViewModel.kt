package com.example.guestapp.presentation.table

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guestapp.domain.usecases.ManageTablesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TableManagementViewModel(
    private val manageTablesUseCase: ManageTablesUseCase,
) : ViewModel() {

    val tableManageViewState = MutableStateFlow(TableManageState.Initial)

    val tablesWithGuests = manageTablesUseCase.getTablesWithGuests().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private fun addTable(
        tableNumber: Int,
        tableName: String,
        capacity: Int,
        onResult: (Result<Long>) -> Unit,
    ) {
        viewModelScope.launch {
            val result = manageTablesUseCase.addTable(tableNumber, tableName, capacity)
            onResult(result)
        }
    }

    fun showDialog() {
        tableManageViewState.update { state -> state.copy(showAddTableDialog = true) }
    }

    fun dismissDialog() {
        tableManageViewState.update { state -> state.copy(showAddTableDialog = false) }
    }

    fun onTableNumberChanged(value: String) {
        tableManageViewState.update { state -> state.copy(tableNumber = value) }
    }

    fun onTableNameChanged(value: String) {
        tableManageViewState.update { state -> state.copy(tableName = value) }
    }

    fun onTableCapacityChanged(value: String) {
        tableManageViewState.update { state -> state.copy(tableCapacity = value) }
    }

    fun onConfirmDialog() {
        val number = tableManageViewState.value.tableNumber.toIntOrNull()
        val capacity = tableManageViewState.value.tableCapacity.toIntOrNull()
        val tableName = tableManageViewState.value.tableName

        if (
            number != null &&
            capacity != null &&
            tableName.isNotBlank()
        ) {
            addTable(
                tableNumber = number,
                tableName = tableName,
                capacity = capacity,
            ) { result -> if (result.isSuccess) clearState() }
        }
    }

    private fun clearState() {
        tableManageViewState.value = TableManageState.Initial
    }
}