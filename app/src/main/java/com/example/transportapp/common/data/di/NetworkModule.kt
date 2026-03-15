package com.example.transportapp.common.data.di

import com.example.transportapp.common.data.RouteApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://drive.usercontent.google.com/") // ← обязательно укажи свою базу URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<RouteApi> {
        get<Retrofit>().create(RouteApi::class.java)
    }
}