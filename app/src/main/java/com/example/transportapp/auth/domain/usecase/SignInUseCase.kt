package com.example.transportapp.auth.domain.usecase

import com.example.transportapp.auth.domain.repository.AuthRepository

class SignInUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Result<Unit> {
        return repository.signIn(email, password)
    }
}