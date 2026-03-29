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
    val lastStopCoords: GeoPoint? = null,
    val intermediateStops:  List<GeoPoint> = emptyList(),
    val stop1: GeoPoint? = null,
    val stop2: GeoPoint? = null,
    val stop3: GeoPoint? = null,
    val stop4: GeoPoint? = null,
    val stop5: GeoPoint? = null,
    val stop6: GeoPoint? = null,
    val stop7: GeoPoint? = null,
    val stop8: GeoPoint? = null,
    val stop9: GeoPoint? = null,
    val stop10: GeoPoint? = null,
)