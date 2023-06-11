package com.ptb.booksearch.network

import com.ptb.booksearch.libraraysearch.data.models.SearchResults
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApi {
    //Search for books that have the search string within the title, author name, text.
    //More info for this api available here https://openlibrary.org/dev/docs/api/search
    @GET("search.json")
    suspend fun search(@Query("q") query: String): SearchResults

    //Returns a jpg cover image for a particular cover id
    //More info for this api available here https://openlibrary.org/dev/docs/api/covers
    @GET("b/id/{coverId}-M.jpg")
    suspend fun getCover(@Path("coverId") coverId : Int): ResponseBody
}