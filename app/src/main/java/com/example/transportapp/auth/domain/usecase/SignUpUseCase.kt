package com.example.transportapp.auth.domain.usecase

import com.example.transportapp.auth.domain.repository.AuthRepository

class SignUpUseCase(private val repo: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return repo.signUp(email, password)
    }
}