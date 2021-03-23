package com.example.reidofifa.firebase

import android.util.Log
import androidx.fragment.app.Fragment
import com.example.reidofifa.fragments.ProfileFragment
import com.example.reidofifa.fragments.ProfileViewModel
import com.example.reidofifa.fragments.SignUpFragment
import com.example.reidofifa.models.Player
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

const val USERS = "Users"

class FirestoreClass {

    private val firestore = FirebaseFirestore.getInstance()

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