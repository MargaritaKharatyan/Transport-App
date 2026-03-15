package com.example.transportapp.main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.transportapp.main.routes.navigation.RoutesDestination
import com.example.transportapp.main.routes.presentation.MapScreen
import com.example.transportapp.main.routes.presentation.RoutesListScreen

@Composable
fun MaiScreen(
    modifier: Modifier = Modifier,
    navigateToSignIn: () -> Unit,
//    navigateToLogin: () -> Unit = {},
//    navigateToMain: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = modifier.padding(top = 15.dp),
            fontSize = 24.sp,
            text = "Main Screen",
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = { navigateToSignIn() }
        ) {
            Text("click")
        }
    }
}

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
                    navigateToSignIn = navigateToSignIn,
                    navigateToMap = {
                        navController.navigate(RoutesDestination.MapScreenDestination)
                    }
                )
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