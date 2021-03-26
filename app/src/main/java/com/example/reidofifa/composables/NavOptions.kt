package com.example.reidofifa.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NavOptions(navOption: NavOption, click: () -> Unit, background: Color) {
    Column(
        modifier = Modifier.padding(top = 8.dp, start = 24.dp, bottom = 8.dp)
    ) {
        TextButton(onClick = click,
            modifier = Modifier.fillMaxWidth()
                .background(background),
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(imageVector = navOption.icon,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = navOption.label,
                    fontSize = 16.sp,
                    color = Color.Black)
            }
        }
    }
}