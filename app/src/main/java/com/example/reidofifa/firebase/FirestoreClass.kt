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

//    fun loadUserDataOnProfile(){
//        firestore.collection(USERS)
//            .document(getCurrentUserID())
//            .get()
//            .addOnSuccessListener { document ->
//                val loggedUser = document.toObject(Player::class.java)
//                ***
//       }
//            .addOnFailureListener { e ->
//                Log.e("TAG", "Error ssssss", e)
//
//            }
//    }


//    fun loadUserData(fragment: Fragment) {
//        firestore.collection(USERS)
//            .document(getCurrentUserID())
//            .get()
//            .addOnSuccessListener { document ->
//                val loggedUser = document.toObject(Player::class.java)
//
//                when (fragment) {
//                    is SignInFragment -> {
//                        fragment.signInSuccess(loggedUser!!)
//                    }
//                    is MainActivity -> {
//                        activity.updateNavigationUserDetails(loggedUser!!)
//
////                    }
//                }
//            }
//            .addOnFailureListener { e ->
//                Log.e("TAG", "Error signing in", e)
//
//            }
//    }
}