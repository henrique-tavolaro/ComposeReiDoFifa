package com.example.reidofifa.fragments

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reidofifa.dependencies.PlayersRepository
import com.example.reidofifa.models.DataOrException
import com.example.reidofifa.models.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OpponentListViewModel @Inject constructor(
    private val repository: PlayersRepository
) : ViewModel() {

    var loading = mutableStateOf(false)
    val data: MutableState<DataOrException<List<Player>, Exception>> = mutableStateOf(
        DataOrException(
            listOf(),
            Exception("")
        )
    )

    val player: MutableState<Player?> = mutableStateOf(null)

    init {
        getOpponents()
        loadUserDetails()
    }

    private fun getOpponents() {
        viewModelScope.launch {
            loading.value = true
            data.value = repository.getAllPlayers()
            loading.value = false
        }
    }

    private fun loadUserDetails() {
        viewModelScope.launch {
            val loggedUser = repository.getPlayerFromFirestore().data
            loggedUser!!.addOnSuccessListener { document ->
                val logged = document.toObject(Player::class.java)
                player.value = logged!!
            }
        }
    }
}
