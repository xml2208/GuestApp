package com.example.guestapp.presentation.guests_list

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.guestapp.R
import com.example.guestapp.data.local.entity.Guest
import com.example.guestapp.presentation.guests_list.composables.GuestCard
import com.example.guestapp.presentation.guests_list.composables.SearchAndFilterSection
import com.example.guestapp.presentation.guests_list.composables.StatsCardContent
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun GuestListScreen(
    onAddGuest: () -> Unit,
    onManageTables: () -> Unit,
    viewModel: GuestListViewModel = koinViewModel()
) {
    val guests by viewModel.guests.collectAsState()
    val guestStats by viewModel.guestStats.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedFilter by viewModel.selectedFilter.collectAsState()

    var showDeleteDialog by remember { mutableStateOf<Guest?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.wedding_tables_title)) },
                colors = TopAppBarDefaults.topAppBarColors(
                ),
                actions = {
                    IconButton(onClick = onManageTables) {
                        Icon(
                            painter = painterResource(R.drawable.ic_tables),
                            contentDescription = null
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddGuest,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    painter = painterResource(R.drawable.add_person),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                StatsCardContent(guestStats)
            }

            item {
                SearchAndFilterSection(
                    searchQuery = searchQuery,
                    selectedFilter = selectedFilter,
                    onSearchQueryChange = viewModel::updateSearchQuery,
                    onFilterChange = viewModel::updateFilter
                )
            }

            items(guests, key = { it.id }) { guest ->
                Log.d("ddk9499", "GuestListScreen: ${guest.side}")
                GuestCard(
                    guest = guest,
                    onDeleteClick = { showDeleteDialog = guest },
                    modifier = Modifier.animateItemPlacement()
                )
            }
        }
    }

    showDeleteDialog?.let { guest ->
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = { Text(stringResource(R.string.delete_guest)) },
            text = { Text("Вы уверены, что хотите удалить ${guest.name}?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteGuest(guest)
                        showDeleteDialog = null
                    }
                ) {
                    Text(stringResource(R.string.delete), color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = null }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}