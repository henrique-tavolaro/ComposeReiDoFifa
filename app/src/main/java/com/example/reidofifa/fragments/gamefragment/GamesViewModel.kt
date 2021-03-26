package com.example.reidofifa.fragments.gamefragment

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reidofifa.dependencies.PlayersRepository
import com.example.reidofifa.models.DataOrException
import com.example.reidofifa.models.Game
import com.example.reidofifa.models.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    private val repository: PlayersRepository
): ViewModel() {

    val player: MutableState<Player?> = mutableStateOf(null)
    val resultP1 = mutableStateOf("")
    val resultP2 = mutableStateOf("")
    val data: MutableState<DataOrException<List<Game>, Exception>> = mutableStateOf(
        DataOrException(
            listOf(),
            Exception("")
        )
    )

    init {
        loadUserDetails()
    }

    fun getAllGames(
        user1: String, user2: String
    ) {
        viewModelScope.launch {
            data.value = repository.getGamesWithOpponent(
                user1, user2
            )
        }
    }

    fun onResultP1Changed(result: String) {

        if (result.length <= 2) {
            this.resultP1.value = result.filter { it.isDigit() }
        }
    }

    fun onResultP2Changed(result: String) {
        if (result.length <= 2) {
            this.resultP2.value = result.filter { it.isDigit() }
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

    fun registerGame(gameHashMap: HashMap<String, String>) {
        viewModelScope.launch {
            repository.registerGame(gameHashMap)
        }
    }
}


