package com.example.reidofifa.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Player(

    val id: String = "",
    val name: String = "",
    val email: String = ""
): Parcelable
