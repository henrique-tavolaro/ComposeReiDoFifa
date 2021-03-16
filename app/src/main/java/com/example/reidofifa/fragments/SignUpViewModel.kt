package com.example.reidofifa.fragments

import android.text.TextUtils
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.reidofifa.firebase.FirestoreClass
import com.example.reidofifa.models.Player
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel
@Inject
constructor() : ViewModel() {

    val name = mutableStateOf("")

    val email = mutableStateOf("")

    val password = mutableStateOf("")

    fun onNameChange(name: String){
        this.name.value = name
    }

    fun onEmailChange(email: String){
        this.email.value = email
    }

    fun onPasswordChange(password: String){
        this.password.value = password
    }


}