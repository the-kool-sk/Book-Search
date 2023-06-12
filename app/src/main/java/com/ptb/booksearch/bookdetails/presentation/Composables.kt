package com.ptb.booksearch.bookdetails.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

@Composable
fun BookDetailsScreen(){
    Box(modifier = Modifier.fillMaxSize()) {
        Column(verticalArrangement = Arrangement.Center) {
            Text(text = "I am detailed Screen")
        }
    }
}