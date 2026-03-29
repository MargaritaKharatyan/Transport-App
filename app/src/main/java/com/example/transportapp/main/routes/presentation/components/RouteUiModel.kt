package com.example.transportapp.main.routes.presentation.components

import com.example.transportapp.common.domain.GeoPointSerializer
import com.google.firebase.firestore.GeoPoint
import kotlinx.serialization.Serializable

@Serializable
data class RouteUiModel(
    val id: Int,
    val type: String,
    val time: String,
    val firstStopName: String?,
    val lastStopName: String?,

    @Serializable(with = GeoPointSerializer::class)
    val firstStopCoords: GeoPoint?,

    @Serializable(with = GeoPointSerializer::class)
    val lastStopCoords: GeoPoint?,

    val intermediateStops: List<@Serializable(with = GeoPointSerializer::class) GeoPoint> = emptyList(),

)