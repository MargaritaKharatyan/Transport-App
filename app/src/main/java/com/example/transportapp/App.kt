package com.example.transportapp

import android.app.Application
import com.example.transportapp.auth.di.authModule
import com.example.transportapp.common.data.di.networkModule
import com.example.transportapp.main.routes.data.di.routesDataModule
import com.example.transportapp.main.routes.domain.di.routesDomainModule
import com.example.transportapp.main.routes.presentation.di.routesPresentationModule
import com.google.firebase.Firebase
import com.google.firebase.initialize
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Firebase.initialize(this) // This ensures Firebase is ready
        startKoin {
            androidContext(this@App)
            modules(
                authModule,
                mainPresentationModule,
                routesDataModule,
                routesDomainModule,
                routesPresentationModule,
                networkModule,
            )
        }
    }
}