package com.example.transportapp.main.routes.domain.repository

interface GetAddressFromCoordsRepository {
    suspend fun getAddress(lat: Double, lng: Double): String?
}