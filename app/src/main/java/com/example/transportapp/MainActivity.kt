package com.example.transportapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.transportapp.auth.prenestation.signIn.SignInViewModel
import com.example.transportapp.common.presentation.navigation.AppDestination
import com.example.transportapp.common.presentation.navigation.AppNavigation
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val appViewModel: SignInViewModel by viewModel()
//    private val startDestination : AppDestination

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            AppNavigation(
                navController = navController,
                startDestination = AppDestination.AuthDestination,
            )
        }
    }
}