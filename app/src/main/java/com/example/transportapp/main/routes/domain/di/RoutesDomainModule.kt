package com.example.transportapp.main.routes.domain.di

import com.example.transportapp.main.routes.domain.usecase.GetRoutesUseCase
import com.example.transportapp.main.routes.domain.usecase.GetRoutesUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val routesDomainModule = module {
    factoryOf(::GetRoutesUseCaseImpl) { bind<GetRoutesUseCase>() }
}