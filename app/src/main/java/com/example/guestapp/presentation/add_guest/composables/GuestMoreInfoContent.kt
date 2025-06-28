package com.example.guestapp.presentation.add_guest.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.guestapp.R
import com.example.guestapp.data.local.entity.WeddingTable
import com.example.guestapp.presentation.add_guest.AddGuestUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuestMoreInfoContent(
    uiState: AddGuestUiState,
    tables: List<WeddingTable>,
    onTableExpandedChange: () -> Unit,
    onDismissTableChoose: () -> Unit,
    onUpdateTable: (tableId: Long?) -> Unit,
    updateDietaryRestrictions: (String) -> Unit,
    updateNotes: (String) -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.accommodation_preferences),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            ExposedDropdownMenuBox(
                expanded = uiState.expandedTable,
                onExpandedChange = { onTableExpandedChange() }
            ) {
                OutlinedTextField(
                    value = tables
                        .find { it.id == uiState.selectedTableId }
                        ?.let { "Стол №${it.tableNumber} - ${it.tableName}" }
                        ?: stringResource(R.string.no_chosen),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(stringResource(R.string.table)) },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = uiState.expandedTable)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = uiState.expandedTable,
                    onDismissRequest = onDismissTableChoose
                ) {
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.no_assigned)) },
                        onClick = { onUpdateTable(null) }
                    )

                    tables.forEach { table ->
                        DropdownMenuItem(
                            text = { Text("Стол №${table.tableNumber} - ${table.tableName}") },
                            onClick = { onUpdateTable(table.id) }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = uiState.dietaryRestrictions,
                onValueChange = updateDietaryRestrictions,
                label = { Text(stringResource(R.string.diet)) },
                leadingIcon = { Icon(Icons.Default.Home, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3
            )

            OutlinedTextField(
                value = uiState.notes,
                onValueChange = updateNotes,
                label = { Text(stringResource(R.string.notes)) },
                leadingIcon = { Icon(Icons.Default.Create, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 4
            )
        }
    }
}