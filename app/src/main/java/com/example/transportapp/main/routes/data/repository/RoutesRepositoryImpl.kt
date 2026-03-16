package com.example.transportapp.main.routes.data.repository

import com.example.transportapp.common.domain.TransportRoute
import com.example.transportapp.main.routes.domain.repository.RoutesRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class RoutesRepositoryImpl(
    private val firestore: FirebaseFirestore
) : RoutesRepository {

    override suspend fun getRoutes(): List<TransportRoute> {
        return try {
            val snapshot = firestore.collection("routes").get().await()
            snapshot.documents.mapNotNull { document ->
                val route = document.toObject(TransportRoute::class.java)
                route?.copy(id = document.id.hashCode())
            }
        } catch (e: Exception) {
            throw e
        }
    }
}