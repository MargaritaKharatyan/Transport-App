package com.example.transportapp.common.domain

import com.google.firebase.firestore.GeoPoint
import kotlinx.serialization.Serializable

@Suppress("SERIALIZER_TYPE_INCOMPATIBLE")
@Serializable(with = GeoPointSerializer::class)
data class TransportRoute(
    val id: Int = 0,
    val type: String = "",
    val time: String = "",
    val firstStopCoords: GeoPoint? = null,
    val lastStopCoords: GeoPoint? = null
)