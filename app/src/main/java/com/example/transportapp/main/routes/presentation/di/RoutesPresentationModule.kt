package com.example.transportapp.main.routes.presentation.di

import com.example.transportapp.main.routes.presentation.RoutesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val routesPresentationModule = module {
    viewModelOf(::RoutesViewModel)
}