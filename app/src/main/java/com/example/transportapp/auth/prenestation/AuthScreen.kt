package com.example.transportapp.auth.prenestation

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
import com.example.transportapp.auth.prenestation.forgotPass.ForgotPassScreen
import com.example.transportapp.auth.prenestation.navigation.AuthDestination
import com.example.transportapp.auth.prenestation.signIn.SignInScreen
import com.example.transportapp.auth.signIn.presentation.SignUpScreen
import com.example.transportapp.common.presentation.navigation.AppDestination

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    navigateToMain: () -> Unit = {},
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
            startDestination = AuthDestination.SignInDestination
        ) {
            composable<AuthDestination.SignInDestination> {
                SignInScreen(
                    navigateToMain = navigateToMain,
                    navigateToSignUp = {
                        navController.navigate(AuthDestination.SignUpDestination)
                    },
                    navigateToForgotPass = {
                        navController.navigate(AuthDestination.ForgotPassDestination)
                    },
                )
            }
            composable<AuthDestination.SignUpDestination> {
                SignUpScreen(
                    navigateToSignIn = {
                        if (!navController.popBackStack()) {
                            navController.navigate(AppDestination.AuthDestination)
                        }
                    },
                )
            }
            composable<AuthDestination.ForgotPassDestination> {
                ForgotPassScreen(
                    navigateToSignIn = {
                        if (!navController.popBackStack()) {
                            navController.navigate(AppDestination.AuthDestination)
                        }
                    },
                )
            }
        }
    }
}