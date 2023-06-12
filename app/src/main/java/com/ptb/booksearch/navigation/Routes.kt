package com.ptb.booksearch.navigation

sealed class Screens(val route : String) {
    object ListingScreen : Screens("listing_screen")
    object BookDetailsScreen : Screens("book_detail_screen")
}