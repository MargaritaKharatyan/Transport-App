package com.example.transportapp

import android.app.Application
import com.example.transportapp.auth.di.authModule
import com.example.transportapp.main.routes.data.di.routesDataModule
import com.example.transportapp.main.routes.domain.di.routesDomainModule
import com.example.transportapp.main.routes.presentation.di.routesPresentationModule
import com.google.firebase.Firebase
import com.google.firebase.initialize
import com.yandex.mapkit.MapKitFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        MapKitFactory.setApiKey("f19a3b58-7fca-4af0-b844-bd1f11fec7fa")
        MapKitFactory.initialize(this)

        Firebase.initialize(this)
        startKoin {
            androidContext(this@App)
            modules(
                authModule,
                mainPresentationModule,
                routesDataModule,
                routesDomainModule,
                routesPresentationModule,
            )
        }
    }
}