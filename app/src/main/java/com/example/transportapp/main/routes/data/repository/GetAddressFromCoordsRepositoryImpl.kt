package com.example.transportapp.main.routes.data.repository

import android.content.Context
import android.location.Geocoder
import com.example.transportapp.main.routes.domain.repository.GetAddressFromCoordsRepository
import java.util.Locale

class GetAddressFromCoordsRepositoryImpl(private val context: Context) :
    GetAddressFromCoordsRepository {
    override suspend fun getAddress(lat: Double, lng: Double): String? {
        val geocoder = Geocoder(context, Locale.getDefault())
        return try {
            val addresses = geocoder.getFromLocation(lat, lng, 1)
            addresses?.firstOrNull()?.let { address ->
                address.thoroughfare ?: address.getAddressLine(0)
            } ?: "Unknown place"
        } catch (e: Exception) {
            "Coordinates: $lat, $lng"
        }
    }
}