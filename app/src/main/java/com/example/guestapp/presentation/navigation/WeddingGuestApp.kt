package com.example.guestapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.guestapp.presentation.table.TableManagementScreen
import com.example.guestapp.presentation.add_guest.AddGuestScreen
import com.example.guestapp.presentation.guests_list.GuestListScreen

@Composable
fun WeddingGuestApp() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = "guest_list"
    ) {
        composable("guest_list") {
            GuestListScreen(
                onAddGuest = { navController.navigate("add_guest") },
                onManageTables = { navController.navigate("table_management") }
            )
        }
        
        composable("add_guest") {
            AddGuestScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable("table_management") {
            TableManagementScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
