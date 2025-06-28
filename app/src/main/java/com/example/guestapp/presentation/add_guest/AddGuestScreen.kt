package com.example.guestapp.presentation.add_guest

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.guestapp.R
import com.example.guestapp.presentation.add_guest.composables.AddGuestTopBar
import com.example.guestapp.presentation.add_guest.composables.GuestContactContent
import com.example.guestapp.presentation.add_guest.composables.GuestMainInfoContent
import com.example.guestapp.presentation.add_guest.composables.GuestMoreInfoContent
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddGuestScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddGuestViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val tables by viewModel.tables.collectAsState(emptyList())

    Scaffold(
        topBar = { AddGuestTopBar(onNavigateBack) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                GuestMainInfoContent(
                    uiState = uiState,
                    onUpdateName = viewModel::updateName,
                    onUpdateAge = viewModel::updateAge,
                    onUpdateGender = viewModel::updateGender,
                    onUpdateSide = viewModel::updateSide,
                    modifier = Modifier
                )
            }

            item {
                GuestContactContent(
                    phoneNumber = uiState.phoneNumber,
                    updatePhone = viewModel::updatePhone
                )
            }

            item {
                Log.d("ddk9499", "AddGuestScreen 2: $tables")
                GuestMoreInfoContent(
                    uiState = uiState,
                    tables = tables,
                    onTableExpandedChange = viewModel::onTableExpandedChange,
                    onDismissTableChoose = viewModel::onDismissTableChoose,
                    onUpdateTable = viewModel::updateTable,
                    updateDietaryRestrictions = viewModel::updateDietaryRestrictions,
                    updateNotes = viewModel::updateNotes
                )
            }

            item {
                Button(
                    onClick = viewModel::addGuest,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    enabled = !uiState.isLoading
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.add_person),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Добавить гостя",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }

    if (uiState.showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissDialog(onBack = onNavigateBack) },
            title = { Text(stringResource(R.string.success)) },
            text = { Text(stringResource(R.string.guest_added)) },
            confirmButton = {
                TextButton(
                    onClick = { viewModel.dismissDialog(onBack = onNavigateBack) },
                    content = { Text(stringResource(R.string.ok)) }
                )
            },
            icon = {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF4CAF50)
                )
            }
        )
    }

    uiState.errorMessage?.let { message ->
        AlertDialog(
            onDismissRequest = viewModel::dismissErrorDialog,
            title = { Text(stringResource(R.string.error)) },
            text = { Text(message) },
            confirmButton = {
                TextButton(
                    onClick = viewModel::dismissErrorDialog,
                    content = { Text(stringResource(R.string.ok)) }
                )
            },
            icon = {
                Icon(
                    Icons.Default.Clear,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            }
        )
    }
}