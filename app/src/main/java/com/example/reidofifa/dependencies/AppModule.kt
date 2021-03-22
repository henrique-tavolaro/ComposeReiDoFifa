package com.example.reidofifa.dependencies


import com.example.reidofifa.firebase.USERS
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
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
    fun provideUserDetails(): Query = FirebaseFirestore.getInstance()
        .collection(USERS)
//        .document(getCurrentUserID(
//             )

//    @Provides
//    fun getCurrentUserID(): String {
//        val currentUser = FirebaseAuth.getInstance().currentUser
//        var currentUserID = ""
//        if (currentUser != null) {
//            currentUserID = currentUser.uid
//        }
//        return currentUserID
//    }

}