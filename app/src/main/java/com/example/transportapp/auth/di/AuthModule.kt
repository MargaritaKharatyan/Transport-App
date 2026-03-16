package com.example.transportapp.auth.di

import com.example.transportapp.auth.data.repository.AuthRepositoryImpl
import com.example.transportapp.auth.data.source.FirebaseAuthSource
import com.example.transportapp.auth.domain.repository.AuthRepository
import com.example.transportapp.auth.domain.usecase.CheckUserSessionUseCase
import com.example.transportapp.auth.domain.usecase.SignInUseCase
import com.example.transportapp.auth.domain.usecase.SignUpUseCase
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import org.koin.dsl.module

val authModule = module {
    single<FirebaseAuth> { Firebase.auth }
    single { FirebaseAuth.getInstance() }
    single { FirebaseAuthSource(get()) }

    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }

    single { SignInUseCase(get()) }
    single { SignUpUseCase(get()) }
    single { CheckUserSessionUseCase(get()) }
}