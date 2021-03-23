package com.example.reidofifa.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(

val id: String = "",
val player1Id: String = "",
val player2Id: String="",
val resultP1: String="",
val resultP2: String="",
val date: String="",
val players: String=""

) : Parcelable

