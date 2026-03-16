package com.example.transportapp

import androidx.lifecycle.ViewModel
import com.example.transportapp.auth.domain.usecase.CheckUserSessionUseCase
import com.example.transportapp.common.presentation.navigation.AppDestination
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(
    private val checkUserSessionUseCase: CheckUserSessionUseCase
) : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    private val _startDestination = MutableStateFlow<Any?>(null)
    val startDestination: StateFlow<Any?> = _startDestination.asStateFlow()

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            _startDestination.value = AppDestination.MainDestination
        } else {
            _startDestination.value = AppDestination.AuthDestination
        }
        _isLoading.value = false
    }
}