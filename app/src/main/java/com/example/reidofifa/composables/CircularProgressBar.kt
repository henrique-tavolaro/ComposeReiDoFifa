package com.example.reidofifa.composables

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable


@Composable
fun circularProgressBar(
    isDisplayed: Boolean
): Boolean {
    if (isDisplayed) {
        CircularProgressIndicator()
    }
    return true
}