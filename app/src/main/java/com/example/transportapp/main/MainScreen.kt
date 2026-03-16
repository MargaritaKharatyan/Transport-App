package com.example.transportapp.main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.transportapp.main.routes.navigation.RoutesDestination
import com.example.transportapp.main.routes.presentation.MapScreen
import com.example.transportapp.main.routes.presentation.RoutesListScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navigateToSignIn: () -> Unit = {},
) {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        NavHost(
            modifier = modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .background(Color.White),
            navController = navController,
            startDestination = RoutesDestination.RoutesScreenDestination
        ) {
            composable<RoutesDestination.RoutesScreenDestination> {
                RoutesListScreen(
                    navigateToSignIn = navigateToSignIn
                ) {
                    navController.navigate(RoutesDestination.MapScreenDestination)
                }
            }
            composable<RoutesDestination.MapScreenDestination> {
                MapScreen(
                    navigateToRoutes = {
                        if (!navController.popBackStack()) {
                            navController.navigate(RoutesDestination.RoutesScreenDestination)
                        }
                    },
                )
            }
        }
    }
}