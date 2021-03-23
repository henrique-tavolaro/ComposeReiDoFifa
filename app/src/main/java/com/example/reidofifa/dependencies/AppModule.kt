package com.example.reidofifa.dependencies


import android.util.Log
import com.example.reidofifa.firebase.USERS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideUserDetails(): DocumentReference =
        FirebaseFirestore.getInstance()
            .collection(USERS)
            .document(getCurrentUserID())

    @Provides
    fun getCurrentUserID(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
    }

    @Provides
    fun getAllUsers(): Query =
        FirebaseFirestore.getInstance()
            .collection(USERS)
            .whereNotEqualTo("id", getCurrentUserID())

//    @Provides
//    fun provideUpdateUserProfileData(userHashMap: HashMap<String, Any>) =
//        FirebaseFirestore.getInstance()
//            .collection(USERS)
//            .document(getCurrentUserID())
//

}


