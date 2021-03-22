package com.example.reidofifa.dependencies


import com.example.reidofifa.models.DataOrException
import com.example.reidofifa.models.Player
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductsRepository @Inject constructor(
    private val queryProductsByName: Query
) {
    suspend fun getPlayerFromFirestore(): DataOrException<List<Player>, Exception> {
        val dataOrException = DataOrException<List<Player>, Exception>()
        try {
            dataOrException.data = queryProductsByName.get().await().map { document ->
                document.toObject(Player::class.java)
            }
        } catch (e: FirebaseFirestoreException) {
            dataOrException.e = e
        }
        return dataOrException
    }
}

