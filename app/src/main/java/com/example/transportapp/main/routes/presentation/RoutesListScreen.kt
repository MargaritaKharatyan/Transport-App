package com.example.transportapp.main.routes.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RoutesListScreen(
    modifier: Modifier = Modifier,
    navigateToSignIn: () -> Unit,
    navigateToMap: () -> Unit,
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
            text = "Routes List Screen",
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = { navigateToMap() }
        ) {
            Text("Map")
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = { navigateToSignIn() }
        ) {
            Text("SignIn")
        }
    }
}