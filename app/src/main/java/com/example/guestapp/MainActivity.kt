package com.example.guestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.guestapp.presentation.navigation.WeddingGuestApp
import com.example.guestapp.ui.theme.GuestAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            GuestAppTheme {
                WeddingGuestApp()
            }
        }
    }
}
