package com.example.guestapp.presentation.table

data class TableManageState(
    val showAddTableDialog: Boolean,
    val tableNumber: String,
    val tableName: String,
    val tableCapacity: String,
) {
    companion object {
        val Initial: TableManageState = TableManageState(
            showAddTableDialog = false,
            tableNumber = "",
            tableName = "",
            tableCapacity = ""
        )
    }
}