package com.example.reidofifa.dependencies


import android.util.Log
import com.example.reidofifa.firebase.USERS
import com.example.reidofifa.util.GAMES
import com.example.reidofifa.util.PLAYERS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
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



//        user1: String, user2: String
    @Provides
    @Named("queryGame")
    fun provideGetAllGames(): Query =
        FirebaseFirestore.getInstance()
                .collection(GAMES)
//                .whereIn(PLAYERS, listOf(user1 + "_" + user2, user2 + "_" + user1))


}


