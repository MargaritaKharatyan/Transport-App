package com.example.transportapp.main.routes.domain.repository

import com.example.transportapp.common.domain.TransportRoute

interface RoutesRepository {
    suspend fun getRoutes(): List<TransportRoute>
}