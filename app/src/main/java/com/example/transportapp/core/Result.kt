package com.example.transportapp.core

sealed class Result<out T> {
    data class Loading<T>(val data: T): Result<T>()
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val message: String, val data: T) : Result<T>()
}