package com.example.transportapp.main.routes.data.repository

import com.example.transportapp.common.data.RouteApi
import com.example.transportapp.common.domain.TransportRoute
import com.example.transportapp.main.routes.domain.repository.RoutesRepository

class RoutesRepositoryImpl(
    private val api: RouteApi
) : RoutesRepository {
    override suspend fun getRoutes(): List<TransportRoute> {
        return api.getRoutes("15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q").routes
    }
}