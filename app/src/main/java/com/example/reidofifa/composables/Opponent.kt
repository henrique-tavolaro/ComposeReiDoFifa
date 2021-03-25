package com.example.reidofifa.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reidofifa.R
import com.example.reidofifa.models.Player

@Composable
fun Opponent(
    player: Player,
    gameCount: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick)

    ) {
        Image(
            modifier = Modifier
                .padding(8.dp)
                .height(50.dp)
                .width(50.dp),
            painter = painterResource(R.drawable.ic_user_place_holder),
            contentDescription = null
        )
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = player.name,
                fontSize = 22.sp
            )
            Text(
                text = "Games Played: $gameCount",
                fontSize = 16.sp
            )
        }
    }
}

