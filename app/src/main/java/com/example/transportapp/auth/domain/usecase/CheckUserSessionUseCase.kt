package com.example.transportapp.auth.domain.usecase

import com.example.transportapp.auth.domain.repository.AuthRepository

class CheckUserSessionUseCase(private val repository: AuthRepository) {
    operator fun invoke(): Boolean = repository.isUserSignedIn()
}