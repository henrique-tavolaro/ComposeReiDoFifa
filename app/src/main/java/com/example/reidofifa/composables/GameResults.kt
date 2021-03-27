package com.example.reidofifa.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.reidofifa.models.Game


@Composable
fun GameResults(game: Game) {
    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = game.date,
        )
        Row(modifier = Modifier.padding(8.dp)) {
            Text(text = game.resultP1)
            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = "x"
            )
            Text(text = game.resultP2)
        }
    }
}

