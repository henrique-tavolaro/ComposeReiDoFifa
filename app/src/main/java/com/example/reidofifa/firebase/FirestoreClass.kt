package com.example.reidofifa.firebase

import com.example.reidofifa.fragments.SignUpFragment
import com.example.reidofifa.models.Player
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

const val USERS = "Users"

class FirestoreClass(){

    val firestore = FirebaseFirestore.getInstance()

    fun registerUser(fragment: SignUpFragment, userInfo: Player) {
        firestore.collection(USERS)
            .document(getCurrentUserID())
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                fragment.userRegisteredSuccess()
            }
    }

    fun getCurrentUserID(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
    }

}