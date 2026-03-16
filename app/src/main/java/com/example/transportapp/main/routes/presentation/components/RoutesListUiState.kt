package com.example.transportapp.main.routes.presentation.components

sealed class RoutesListUiState {
    data object Loading : RoutesListUiState()
    data class Success(val data: List<RouteUiModel>) : RoutesListUiState()
    data class Error(val message: String) : RoutesListUiState()
}