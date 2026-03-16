package com.example.transportapp.common.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.transportapp.R
import com.example.transportapp.auth.prenestation.AuthScreen
import com.example.transportapp.main.MainScreen
import com.google.firebase.auth.FirebaseAuth

val auth = FirebaseAuth.getInstance()

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: AppDestination,
) {
    Scaffold(
        Modifier
            .fillMaxSize()
            .background(colorResource(R.color.screen_background))
    ) {
        NavHost(
            modifier = Modifier
                .background(colorResource(R.color.screen_background))
                .systemBarsPadding()
                .navigationBarsPadding(),
            navController = navController,
            startDestination = startDestination,
        ) {
            composable<AppDestination.AuthDestination> {
                AuthScreen(
                    navigateToMain = {
                        navController.navigate(AppDestination.MainDestination)
                    },
                )
            }

            composable<AppDestination.MainDestination> {
                MainScreen(
                    navigateToSignIn = {
                        auth.signOut()
                        navController.navigate(AppDestination.AuthDestination) {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                )
            }
        }
    }
}