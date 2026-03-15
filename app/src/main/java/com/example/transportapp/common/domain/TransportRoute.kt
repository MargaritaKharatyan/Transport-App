package com.example.transportapp.common.domain

import kotlinx.serialization.Serializable

@Serializable
data class TransportRoute(
    val id: Int,
    val type: String,
    val time: String,
    val firstStop: String,
    val lastStop: String,
)