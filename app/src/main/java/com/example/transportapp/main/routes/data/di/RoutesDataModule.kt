package com.example.transportapp.main.routes.data.di

import com.example.transportapp.main.routes.data.repository.RoutesRepositoryImpl
import com.example.transportapp.main.routes.domain.repository.RoutesRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val routesDataModule = module {
    singleOf(::RoutesRepositoryImpl) { bind<RoutesRepository>() }
}