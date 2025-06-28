package com.example.guestapp.presentation.guests_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guestapp.domain.models.GuestStats
import com.example.guestapp.data.local.entity.Guest
import com.example.guestapp.domain.models.WeddingSide
import com.example.guestapp.domain.repository.GuestRepository
import com.example.guestapp.domain.usecases.GetGuestStatsUseCase
import com.example.guestapp.domain.usecases.SearchGuestsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GuestListViewModel(
    private val repository: GuestRepository,
    private val searchGuestsUseCase: SearchGuestsUseCase,
    getGuestStatsUseCase: GetGuestStatsUseCase
) : ViewModel() {
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()
    
    private val _selectedFilter = MutableStateFlow(FilterType.ALL)
    val selectedFilter = _selectedFilter.asStateFlow()
    
    val guestStats = getGuestStatsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = GuestStats.Initial
    )
    
    @OptIn(ExperimentalCoroutinesApi::class)
    val guests = combine(
        searchQuery,
        selectedFilter
    ) { query, filter ->
        if (query.isBlank()) {
            repository.getAllGuests()
        } else {
            searchGuestsUseCase(query)
        }
    }.flatMapLatest { it }
        .combine(selectedFilter) { guests, filter ->
            when (filter) {
                FilterType.ALL -> guests
                FilterType.CONFIRMED -> guests.filter { it.isConfirmed }
                FilterType.PENDING -> guests.filter { !it.isConfirmed }
                FilterType.BRIDE -> guests.filter { it.side == WeddingSide.BRIDE }
                FilterType.GROOM -> guests.filter { it.side == WeddingSide.GROOM }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
    
    fun updateFilter(filter: FilterType) {
        _selectedFilter.value = filter
    }
    
    fun deleteGuest(guest: Guest) {
        viewModelScope.launch {
            repository.deleteGuest(guest)
        }
    }
}