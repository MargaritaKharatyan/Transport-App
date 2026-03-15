package com.example.transportapp.auth.prenestation.navigation

import kotlinx.serialization.Serializable

sealed interface AuthDestination {
    @Serializable
    data object SignInDestination : AuthDestination

    @Serializable
    data object SignUpDestination : AuthDestination

    @Serializable
    data object ForgotPassDestination : AuthDestination{
    }
}