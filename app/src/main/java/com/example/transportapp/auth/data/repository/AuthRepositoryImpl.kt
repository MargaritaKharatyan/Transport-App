package com.example.transportapp.auth.data.repository

import com.example.transportapp.auth.data.source.FirebaseAuthSource
import com.example.transportapp.auth.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth

class AuthRepositoryImpl(
    private val firebaseSource: FirebaseAuthSource,
    private val auth: FirebaseAuth
) : AuthRepository {

    override suspend fun signIn(email: String, password: String): Result<Unit> {
        return firebaseSource.signIn(email, password)
    }

    override suspend fun signUp(email: String, password: String): Result<Unit> {
        return firebaseSource.signUp(email, password)
    }

    override fun isUserSignedIn(): Boolean {
        return auth.currentUser != null
    }
}