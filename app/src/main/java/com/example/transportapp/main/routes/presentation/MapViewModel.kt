package com.example.transportapp.main.routes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transportapp.main.routes.data.repository.GetAddressFromCoordsRepositoryImpl
import com.example.transportapp.main.routes.domain.usecase.GetRoutesUseCase
import com.example.transportapp.main.routes.presentation.components.RouteUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Collections.emptyList

class MapViewModel(
    private val getRoutesUseCase: GetRoutesUseCase,
    private val locationService: GetAddressFromCoordsRepositoryImpl
) : ViewModel() {
    private val _routePoints = MutableStateFlow<List<RouteUiModel>>(emptyList())
    val routePoints = _routePoints.asStateFlow()

    fun fetchRouteById(routeId: Int) {
        viewModelScope.launch {
            try {
                val routes = getRoutesUseCase()
                val route = routes.find { it.id == routeId }
                route?.let {
                    val startAddress = it.firstStopCoords?.let { coords ->
                        locationService.getAddress(coords.latitude, coords.longitude)
                    }
                    val endAddress = it.lastStopCoords?.let { coords ->
                        locationService.getAddress(coords.latitude, coords.longitude)
                    }

                    _routePoints.value = listOf(
                        RouteUiModel(
                            id = it.id,
                            type = it.type,
                            time = it.time,
                            firstStopName = startAddress,
                            lastStopName = endAddress,
                            firstStopCoords = it.firstStopCoords,
                            lastStopCoords = it.lastStopCoords,
                            intermediateStops = it.intermediateStops,
                        )
                    )
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}