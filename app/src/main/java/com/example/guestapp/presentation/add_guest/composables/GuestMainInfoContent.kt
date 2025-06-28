package com.example.guestapp.presentation.add_guest.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.guestapp.R
import com.example.guestapp.domain.models.Gender
import com.example.guestapp.domain.models.WeddingSide
import com.example.guestapp.presentation.add_guest.AddGuestUiState

@Composable
fun GuestMainInfoContent(
    uiState: AddGuestUiState,
    onUpdateName: (String) -> Unit,
    onUpdateAge: (String) -> Unit,
    onUpdateGender: (Gender) -> Unit,
    onUpdateSide: (WeddingSide) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.main_info),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            OutlinedTextField(
                value = uiState.name,
                onValueChange = onUpdateName,
                label = { Text(stringResource(R.string.name_guest)) },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = uiState.name.isBlank()
            )

            OutlinedTextField(
                value = uiState.age,
                onValueChange = onUpdateAge,
                label = { Text(stringResource(R.string.age_guest)) },
                leadingIcon = {
                    Icon(Icons.Default.CheckCircle, null)
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = uiState.age.toIntOrNull() == null || (uiState.age.toIntOrNull()
                    ?: 0) <= 0
            )

            Text(
                text = stringResource(R.string.gender),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Gender.entries.forEach { gender ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable { onUpdateGender(gender) }
                            .padding(4.dp)
                    ) {
                        RadioButton(
                            selected = uiState.gender == gender,
                            onClick = { onUpdateGender(gender) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = when (gender) {
                                Gender.MALE -> stringResource(R.string.male_guest)
                                Gender.FEMALE -> stringResource(R.string.female_guest)
                            }
                        )
                    }
                }
            }

            Text(
                text = stringResource(R.string.side),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                WeddingSide.entries.forEach { side ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable { onUpdateSide(side) }
                            .padding(4.dp)
                    ) {
                        RadioButton(
                            selected = uiState.side == side,
                            onClick = { onUpdateSide(side) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = if (side == WeddingSide.BRIDE) Color(
                                    color = 0xFFE91E63
                                ) else Color(0xFF2196F3)
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (side == WeddingSide.BRIDE) stringResource(R.string.bride) else stringResource(
                                R.string.groom
                            ),
                            color = if (uiState.side == side) {
                                if (side == WeddingSide.BRIDE) Color(0xFFE91E63) else Color(
                                    0xFF2196F3
                                )
                            } else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}
