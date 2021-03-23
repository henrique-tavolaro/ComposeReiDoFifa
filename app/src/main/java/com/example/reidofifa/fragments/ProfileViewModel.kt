package com.example.reidofifa.fragments

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reidofifa.dependencies.PlayersRepository
import com.example.reidofifa.models.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: PlayersRepository
) : ViewModel() {

    var loading = mutableStateOf(false)

    val data: MutableState<Player?> = mutableStateOf(null)


    init {
        getUser()
    }

    private fun getUser() {
        loading.value = true
        viewModelScope.launch {
            val loggedUser = repository.getPlayerFromFirestore().data
            loggedUser!!.addOnSuccessListener { document ->
               val logged = document.toObject(Player::class.java)
                data.value = logged!!
                loading.value = false
            }
        }
    }

//    fun updateUserDetails(userHashMap: HashMap<String, Any>) {
//        repository.updateUserDetails(userHashMap)
//    }

}