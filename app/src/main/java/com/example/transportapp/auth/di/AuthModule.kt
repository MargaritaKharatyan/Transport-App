package com.example.transportapp.auth.di

import com.example.transportapp.auth.data.repository.AuthRepositoryImpl
import com.example.transportapp.auth.data.source.FirebaseAuthSource
import com.example.transportapp.auth.domain.repository.AuthRepository
import com.example.transportapp.auth.domain.usecase.SignInUseCase
import com.example.transportapp.auth.domain.usecase.SignUpUseCase
import com.example.transportapp.auth.prenestation.registration.SignUpViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.ktx.Firebase
import org.koin.dsl.module

val authModule = module {

    single<FirebaseAuth> { Firebase.auth } // FirebaseAuth instance

    single { FirebaseAuthSource(get()) } // inject FirebaseAuth into source

    single<AuthRepository> { AuthRepositoryImpl(get()) } // inject source into repository

    single { SignInUseCase(get()) } // inject repository into use case
    single { SignUpUseCase(get()) }

}