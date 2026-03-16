package com.example.transportapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.transportapp.common.presentation.navigation.AppDestination
import com.example.transportapp.common.presentation.navigation.AppNavigation
import com.example.transportapp.ui.theme.TransportAppTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val mainViewModel = getViewModel<MainViewModel>()

            val isLoading by mainViewModel.isLoading.collectAsState()
            val startDestination by mainViewModel.startDestination.collectAsState()

            TransportAppTheme {
                if (isLoading || startDestination == null) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    AppNavigation(
                        navController = navController,
                        startDestination = startDestination as AppDestination
                    )
                }
            }
        }
    }
}