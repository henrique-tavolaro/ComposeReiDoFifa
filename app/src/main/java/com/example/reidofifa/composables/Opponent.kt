package com.example.reidofifa.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reidofifa.R
import com.example.reidofifa.models.Player
import com.example.reidofifa.util.DEFAULT_IMAGE

@Composable
fun Opponent(
    player: Player,
    gameCount: Int,
    onClick: () -> Unit
) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                .padding(4.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(


        ) { val image =
            loadImage(url = player.image, defaultImage = DEFAULT_IMAGE).value
            image?.let { img ->
                Image(
                    bitmap = img.asImageBitmap(),
                    modifier = Modifier
                        .padding(8.dp)
                        .height(60.dp)
                        .width(60.dp)
                        .clip(CircleShape),
//                contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }
//        Image(
//            modifier = Modifier
//                .padding(8.dp)
//                .height(50.dp)
//                .width(50.dp),
//            painter = painterResource(R.drawable.ic_user_place_holder),
//            contentDescription = null
//        )
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = player.name,
                    fontSize = 22.sp
                )
                if(gameCount == 0) {
                    Text(
                        text = "Games Played: not started",
                        fontSize = 16.sp)
                } else {
                    Text(
                        text = "Games Played: $gameCount",
                        fontSize = 16.sp
                    )
                }
            }
        }
    }

}

