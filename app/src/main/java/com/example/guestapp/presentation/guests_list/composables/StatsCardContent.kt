package com.example.guestapp.presentation.guests_list.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.guestapp.R
import com.example.guestapp.domain.models.GuestStats

@Composable
fun StatsCardContent(stats: GuestStats) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = stringResource(R.string.guests_stats_title),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatItem(
                    label = stringResource(R.string.total),
                    value = stats.totalGuests.toString(),
                    iconContent = {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                )

                StatItem(
                    label = stringResource(R.string.confermed),
                    value = stats.confirmedGuests.toString(),
                    iconContent = {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF4CAF50)
                        )
                    },
                )

                StatItem(
                    label = stringResource(R.string.tables),
                    value = "${stats.tablesOccupied}",
                    iconContent = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                        )
                    },
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatItem(
                    iconContent = {
                        Icon(
                            painter = painterResource(R.drawable.ic_bride),
                            contentDescription = null,
                            tint = Color(0xFFE91E63)
                        )

                    },
                    label = stringResource(R.string.bride),
                    value = stats.brideGuests.toString(),
                )

                StatItem(
                    modifier = Modifier.padding(top = 4.dp),
                    label = stringResource(R.string.groom),
                    value = stats.groomGuests.toString(),
                    iconContent = {
                        Icon(
                            painter = painterResource(R.drawable.ic_groom),
                            contentDescription = null,
                            tint = Color(0xFF2196F3)
                        )
                    }
                )

                StatItem(
                    modifier = Modifier.padding(top = 4.dp),
                    label = stringResource(R.string.places),
                    value = stats.totalCapacity.toString(),
                    iconContent = {
                        Icon(
                            painter = painterResource(R.drawable.ic_table),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun StatItem(
    iconContent: @Composable () -> Unit,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        iconContent()

        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

