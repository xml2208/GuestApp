package com.example.guestapp.di

import androidx.room.Room
import com.example.guestapp.data.local.WeddingDatabase
import com.example.guestapp.data.repository.GuestRepositoryImpl
import com.example.guestapp.domain.repository.GuestRepository
import com.example.guestapp.domain.usecases.AddGuestUseCase
import com.example.guestapp.domain.usecases.GetGuestStatsUseCase
import com.example.guestapp.domain.usecases.ManageTablesUseCase
import com.example.guestapp.domain.usecases.SearchGuestsUseCase
import com.example.guestapp.presentation.table.TableManagementViewModel
import com.example.guestapp.presentation.add_guest.AddGuestViewModel
import com.example.guestapp.presentation.guests_list.GuestListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = WeddingDatabase::class.java,
            name = "wedding_database"
        ).build()
    }

    single { get<WeddingDatabase>().guestDao() }

    single { get<WeddingDatabase>().tableDao() }

    single<GuestRepository> {
        GuestRepositoryImpl(
            guestDao = get(),
            tableDao = get(),
        )
    }

    single { AddGuestUseCase(repository = get()) }

    single { GetGuestStatsUseCase(repository = get()) }

    single { SearchGuestsUseCase(repository = get()) }

    single { ManageTablesUseCase(repository = get()) }

    viewModel {
        GuestListViewModel(
            repository = get(),
            searchGuestsUseCase = get(),
            getGuestStatsUseCase = get(),
        )
    }

    viewModel {
        AddGuestViewModel(
            addGuestUseCase = get(),
            repository = get()
        )
    }

    viewModel { TableManagementViewModel(manageTablesUseCase = get()) }
}