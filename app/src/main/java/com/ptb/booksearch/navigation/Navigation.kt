package com.ptb.booksearch.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ptb.booksearch.bookdetails.presentation.BookDetailsScreen
import com.ptb.booksearch.libraraysearch.presentation.composables.BooksListingScreen
import com.ptb.booksearch.libraraysearch.presentation.composables.HomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.ListingScreen.route) {
        composable(route = Screens.ListingScreen.route) {
            BooksListingScreen(navController)
        }
        composable(route = Screens.BookDetailsScreen.route) {
            BookDetailsScreen()
        }
    }

}