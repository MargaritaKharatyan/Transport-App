package com.example.transportapp.main.routes.data.di

import com.example.transportapp.main.routes.data.repository.GetAddressFromCoordsRepositoryImpl
import com.example.transportapp.main.routes.data.repository.RoutesRepositoryImpl
import com.example.transportapp.main.routes.domain.repository.GetAddressFromCoordsRepository
import com.example.transportapp.main.routes.domain.repository.RoutesRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val routesDataModule = module {
    single { Firebase.firestore }
    singleOf(::RoutesRepositoryImpl) { bind<RoutesRepository>() }
    singleOf(::GetAddressFromCoordsRepositoryImpl) { bind<GetAddressFromCoordsRepository>() }
}