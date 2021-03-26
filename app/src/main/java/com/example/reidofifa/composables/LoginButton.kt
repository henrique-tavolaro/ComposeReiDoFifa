package com.example.reidofifa.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.reidofifa.ui.theme.PrimaryColor
import com.example.reidofifa.ui.theme.PrimaryLightColor

@Composable
fun LoginButton(
    onButtonClick : () -> Unit,
    text : String,
    color: Color
){
    Surface(        modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .clickable(onClick = onButtonClick)
        ,
        color = color,
        shape = MaterialTheme.shapes.medium,
        elevation = 4.dp,
        border = BorderStroke(1.dp,
            color = MaterialTheme.colors.primary)
    ){
        Column() {

            Text(text = text,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.h5
            )
        }
    }
}
