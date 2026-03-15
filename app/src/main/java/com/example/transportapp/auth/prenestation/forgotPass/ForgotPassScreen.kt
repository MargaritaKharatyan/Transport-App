package com.example.transportapp.auth.prenestation.forgotPass

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
fun ForgotPassScreen(
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
            text = "Reset password Screen",
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = { navigateToSignIn() }
        ) {
            Text("click")
        }
    }
}