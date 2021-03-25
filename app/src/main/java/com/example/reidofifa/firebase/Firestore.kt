package com.example.reidofifa.firebase

import android.util.Log
import com.example.reidofifa.dependencies.AppModule.getCurrentUserID
import com.example.reidofifa.util.GAMES
import com.example.reidofifa.util.PLAYERS
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import javax.inject.Inject

class Firestore @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    suspend fun updateUserProfileData(userHashMap: HashMap<String, Any>) {
        firestore.collection(USERS)
            .document(getCurrentUserID())
            .update(userHashMap)
            .addOnSuccessListener {
                Log.i("TAGS", "profile data updated successfully")

            }
            .addOnFailureListener { e ->
                Log.e("TAGS", "profile data error", e)
            }
    }

    suspend fun registerGame(gameHashMap: HashMap<String, String>) {
        firestore.collection(GAMES)
            .add(gameHashMap)
            .addOnSuccessListener {
                val id = it.id
                it.update("id", id)
            }
    }




}