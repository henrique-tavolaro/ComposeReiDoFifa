package com.example.reidofifa.firebase

import android.util.Log
import com.example.reidofifa.dependencies.AppModule.getCurrentUserID
import com.google.firebase.firestore.FirebaseFirestore
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


}