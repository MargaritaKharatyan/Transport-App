package com.example.transportapp.main.routes.domain.usecase

import com.example.transportapp.common.domain.TransportRoute
import com.example.transportapp.main.routes.domain.repository.RoutesRepository

interface GetRoutesUseCase {
    operator suspend fun invoke(): List<TransportRoute>
}

class GetRoutesUseCaseImpl(val repository: RoutesRepository) : GetRoutesUseCase {
    override suspend fun invoke() = repository.getRoutes()
}