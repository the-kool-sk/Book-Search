package com.ptb.booksearch.libraraysearch.domain.repository

import com.ptb.booksearch.libraraysearch.data.models.SearchResults
import com.ptb.booksearch.network.ApiState
import kotlinx.coroutines.flow.Flow

interface LibraryRepository {
    suspend fun getBooks(query: String): Flow<ApiState<SearchResults>>
}