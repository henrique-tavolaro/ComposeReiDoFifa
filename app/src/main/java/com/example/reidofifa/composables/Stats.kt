package com.example.reidofifa.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun Stats(
    win: Int,
    draw: Int,
    lost: Int
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(90.dp)
            .height(90.dp),
        elevation = 6.dp,
        shape = CircleShape
    ) {
        Column {

            Text(
                text = "Stats",
                fontSize = 16.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 4.dp)
            )
            Divider(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .height(1.dp)
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "W",
                    fontSize = 14.sp
                )
                Text(
                    text = "D",
                    fontSize = 14.sp
                )
                Text(
                    text = "L",
                    fontSize = 14.sp
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = win.toString(),
                    fontSize = 13.sp
                )
                Text(
                    text = draw.toString(),
                    fontSize = 13.sp
                )
                Text(
                    text = lost.toString(),
                    fontSize = 13.sp
                )
            }

        }
    }
}
