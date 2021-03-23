package com.example.reidofifa.dependencies


import com.example.reidofifa.models.DataOrException
import com.example.reidofifa.models.Player
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayersRepository @Inject constructor(
    private val getUser: DocumentReference,
    private val queryPlayers: Query
) {
    suspend fun getPlayerFromFirestore(): DataOrException<Task<DocumentSnapshot>, Exception> {
        val dataOrException = DataOrException<Task<DocumentSnapshot>, Exception>()
        try {
            dataOrException.data = getUser.get()
        } catch (e: FirebaseFirestoreException) {
            dataOrException.e = e
        }
        return dataOrException
    }

        suspend fun getAllPlayers() : DataOrException<List<Player>, Exception> {
            val dataOrException = DataOrException<List<Player>, Exception>()
            try {
                dataOrException.data = queryPlayers.get().await().map { document ->
                    document.toObject(Player::class.java)
                }
            } catch (e: FirebaseFirestoreException) {
                dataOrException.e = e
            }
            return dataOrException
}
//    fun updateUserDetails(userHashMap: HashMap<String, Any>) {
//        return updateUserDetail(userHashMap)
    }


