package com.example.transportapp.main.routes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transportapp.common.domain.TransportRoute
import com.example.transportapp.main.routes.data.repository.GetAddressFromCoordsRepositoryImpl
import com.example.transportapp.main.routes.domain.repository.RoutesRepository
import com.example.transportapp.main.routes.domain.usecase.GetRoutesUseCase
import com.example.transportapp.main.routes.presentation.components.RouteUiModel
import com.example.transportapp.main.routes.presentation.components.RoutesListUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RoutesViewModel(
    private val getRoutesUseCase: GetRoutesUseCase,
    val repository: RoutesRepository,
    val locationService: GetAddressFromCoordsRepositoryImpl
) : ViewModel() {
    private val _routesUiState = MutableStateFlow<RoutesListUiState>(RoutesListUiState.Loading)
    val routesUiState: StateFlow<RoutesListUiState> = _routesUiState.asStateFlow()
    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()
    private var allRoutes: List<RouteUiModel> = emptyList()
    val routes: List<RouteUiModel> get() = allRoutes

    init {
        fetchRoutes()
    }

    fun fetchRoutes() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val rawRoutes: List<TransportRoute> = getRoutesUseCase()

                val uiModels = rawRoutes.map { route ->

                    val latStart = route.firstStopCoords?.latitude ?: 0.0
                    val lngStart = route.firstStopCoords?.longitude ?: 0.0
                    android.util.Log.d(
                        "GEOCODER_TEST",
                        "Route[${route.id}] - Start: lat=$latStart, lng=$lngStart"
                    )

                    val startAddress = locationService.getAddress(
                        route.firstStopCoords?.latitude ?: 0.0,
                        route.firstStopCoords?.longitude ?: 0.0
                    )

                    val endAddress = locationService.getAddress(
                        route.lastStopCoords?.latitude ?: 0.0,
                        route.lastStopCoords?.longitude ?: 0.0
                    )

                    RouteUiModel(
                        id = route.id,
                        type = route.type,
                        time = route.time,
                        firstStopName = startAddress,
                        lastStopName = endAddress,
                        firstStopCoords = route.firstStopCoords,
                        lastStopCoords = route.lastStopCoords
                    )
                }

                allRoutes = uiModels
                _routesUiState.value = RoutesListUiState.Success(uiModels)

            } catch (e: Exception) {
                _routesUiState.value = RoutesListUiState.Error(e.message ?: "Error")
            }
        }
    }

    fun onSearchText(text: String) {
        _searchText.value = text
        applySearchFilter(text)
    }

    fun onClearSearch() {
        _searchText.value = ""
        applySearchFilter("")
    }

    private fun applySearchFilter(query: String) {
        val filteredList = if (query.isBlank()) {
            allRoutes
        } else {
            allRoutes.filter { route ->
                route.type.contains(query, ignoreCase = true) ||
                        route.firstStopName?.contains(query, ignoreCase = true) == true ||
                        route.lastStopName?.contains(query, ignoreCase = true) == true
            }
        }
        _routesUiState.value = RoutesListUiState.Success(filteredList)
    }

    fun sortRoutesByTime() {
        val currentState = _routesUiState.value
        if (currentState is RoutesListUiState.Success) {

            val calendar = java.util.Calendar.getInstance()
            val currentSecondsFromMidnight = (calendar.get(java.util.Calendar.HOUR_OF_DAY) * 3600) +
                    (calendar.get(java.util.Calendar.MINUTE) * 60) +
                    calendar.get(java.util.Calendar.SECOND)

            val sortedList = currentState.data.sortedBy { route ->
                val intervalMinutes = route.time.filter { it.isDigit() }.toIntOrNull() ?: 10
                val intervalInSeconds = intervalMinutes * 60

                val secondsPassedInCycle = currentSecondsFromMidnight % intervalInSeconds
                val remainingSeconds = intervalInSeconds - secondsPassedInCycle

                android.util.Log.d(
                    "SORT_DEBUG",
                    "Route ${route.id}: Осталось $remainingSeconds сек."
                )

                remainingSeconds
            }

            allRoutes = sortedList
            _routesUiState.value = RoutesListUiState.Success(sortedList)
        }
    }
}