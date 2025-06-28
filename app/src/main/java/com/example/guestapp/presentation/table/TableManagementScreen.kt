package com.example.guestapp.presentation.table

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.guestapp.R
import com.example.guestapp.presentation.table.composables.TableCard
import com.example.guestapp.presentation.table.composables.TableManageTopBar
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TableManagementScreen(
    onNavigateBack: () -> Unit,
    viewModel: TableManagementViewModel = koinViewModel(),
) {
    val viewState by viewModel.tableManageViewState.collectAsState()
    val tablesWithGuests by viewModel.tablesWithGuests.collectAsState()

    Scaffold(
        topBar = { TableManageTopBar(onNavigateBack) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = viewModel::showDialog,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_table),
                    tint = Color.White
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(
                items = tablesWithGuests,
                key = { it.table.id },
                itemContent = { tableWithGuests ->
                    TableCard(
                        tableWithGuests = tableWithGuests,
                        modifier = Modifier.animateItemPlacement()
                    )
                })
        }
    }

    if (viewState.showAddTableDialog) {
        AlertDialog(
            onDismissRequest = viewModel::dismissDialog,
            title = { Text(stringResource(R.string.add_table)) },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = viewState.tableNumber,
                        onValueChange = viewModel::onTableNumberChanged,
                        label = { Text(stringResource(R.string.table_number)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = viewState.tableName,
                        onValueChange = viewModel::onTableNameChanged,
                        label = { Text(stringResource(R.string.table_name)) },
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = viewState.tableCapacity,
                        onValueChange = viewModel::onTableCapacityChanged,
                        label = { Text(stringResource(R.string.capacity)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = viewModel::onConfirmDialog,
                    content = { Text(stringResource(R.string.add)) }
                )
            },

            dismissButton = {
                TextButton(
                    onClick = viewModel::dismissDialog,
                    content = { Text(stringResource(R.string.cancel)) },
                )
            }
        )
    }
}

