package com.example.transportapp.main.routes.domain.model

import com.example.transportapp.common.domain.TransportRoute

data class RoutesResponse(
    val routes: List<TransportRoute>
)