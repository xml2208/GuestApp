package com.example.guestapp.presentation.table.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.guestapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TableManageTopBar(
    onBack: () -> Unit,
) {
    TopAppBar(
        title = { Text(stringResource(R.string.manage_tables)) },
        navigationIcon = {
            IconButton(
                onClick = onBack,
                content = { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null) })
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    )
}

