package com.ptb.booksearch.libraraysearch.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.ptb.booksearch.libraraysearch.presentation.composables.HomeScreen
import com.ptb.booksearch.libraraysearch.presentation.viewmodels.LibrarySearchViewModel
import com.ptb.booksearch.ui.theme.BookSearchAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val librarySearchViewModel = viewModels<LibrarySearchViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookSearchAppTheme(
                content = {
                    val state = librarySearchViewModel.value.searchResultsFlow.collectAsState()
                    val queryText = remember { mutableStateOf("") }
                    HomeScreen(queryText.value, state.value) { updatedText ->
                        queryText.value = updatedText
                    }
                    LaunchedEffect(queryText.value) {
                        delay(300)
                        librarySearchViewModel.value.search(queryText.value)
                    }
                }
            )
        }
    }
}


//->
//<-
