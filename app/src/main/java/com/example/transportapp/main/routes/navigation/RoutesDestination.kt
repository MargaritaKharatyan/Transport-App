package com.example.transportapp.main.routes.navigation

import kotlinx.serialization.Serializable

sealed interface RoutesDestination {
    @Serializable
    data object RoutesScreenDestination : RoutesDestination

    @Serializable
    data object MapScreenDestination : RoutesDestination
}