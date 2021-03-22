package com.example.reidofifa.fragments

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reidofifa.dependencies.ProductsRepository
import com.example.reidofifa.firebase.FirestoreClass
import com.example.reidofifa.models.DataOrException
import com.example.reidofifa.models.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
 private val repository: ProductsRepository
) : ViewModel() {

    var loading = mutableStateOf(false)

    val data: MutableState<DataOrException<List<Player>, Exception>> = mutableStateOf(
        DataOrException(
            listOf(),
            Exception("")
        )
    )

    init {
        getUser()
    }

    private fun getUser(){
        viewModelScope.launch {
            loading.value = true
            data.value = repository.getPlayerFromFirestore()
            loading.value = false
        }
    }

}