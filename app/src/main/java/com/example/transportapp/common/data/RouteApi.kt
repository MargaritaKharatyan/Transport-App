package com.example.transportapp.common.data

import com.example.transportapp.main.routes.domain.model.RoutesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RouteApi {
    @GET("u/0/uc")
    suspend fun getRoutes(
        @Query("id") id: String,
        @Query("export") export: String = "download"
    ): RoutesResponse
}