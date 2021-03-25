package com.example.reidofifa.dependencies


import com.example.reidofifa.firebase.Firestore
import com.example.reidofifa.models.DataOrException
import com.example.reidofifa.models.Game
import com.example.reidofifa.models.Player
import com.example.reidofifa.util.PLAYERS
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class PlayersRepository @Inject constructor(
    private val getUser: DocumentReference,
    private val queryPlayers: Query,
    @Named ("queryGame")
    private val queryGame: Query,
    private val firestoreClass: Firestore
) {
    fun getPlayerFromFirestore(): DataOrException<Task<DocumentSnapshot>, Exception> {
        val dataOrException = DataOrException<Task<DocumentSnapshot>, Exception>()
        try {
            dataOrException.data = getUser.get()
        } catch (e: FirebaseFirestoreException) {
            dataOrException.e = e
        }
        return dataOrException
    }

    suspend fun getAllGames(): DataOrException<List<Game>, Exception> {
        val dataOrException = DataOrException<List<Game>, Exception>()
        try {

            dataOrException.data =
                queryGame.get()
                    .await().map { document ->
                    document.toObject(Game::class.java)
                }
        } catch (e: FirebaseFirestoreException) {
            dataOrException.e = e
        }
        return dataOrException
    }

    suspend fun getGamesWithOpponent(
        user1: String, user2: String
    ): DataOrException<List<Game>, Exception> {
        val dataOrException = DataOrException<List<Game>, Exception>()
        try {

            dataOrException.data = queryGame.whereIn(PLAYERS, listOf(user1 + "_" + user2, user2 + "_" + user1)).
            get().await().map { document ->
                document.toObject(Game::class.java)
            }
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
    suspend fun updateUserProfile(userHashMap: HashMap<String, Any>) {
        firestoreClass.updateUserProfileData(userHashMap)
    }

   suspend fun registerGame(gameHashMap: HashMap<String, String>) {
        val data = firestoreClass.registerGame(gameHashMap)
   }





//    fun updateUserDetails(userHashMap: HashMap<String, Any>) {
//        return updateUserDetail(userHashMap)
    }


