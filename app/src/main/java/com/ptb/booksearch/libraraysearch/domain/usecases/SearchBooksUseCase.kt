package com.ptb.booksearch.libraraysearch.domain.usecases

import com.ptb.booksearch.libraraysearch.data.models.SearchResults
import com.ptb.booksearch.libraraysearch.domain.repository.LibraryRepository
import com.ptb.booksearch.network.ApiState
import kotlinx.coroutines.flow.Flow

class SearchBooksUseCase (private val repository: LibraryRepository) {
    suspend operator fun invoke(query: String): Flow<ApiState<SearchResults>> {
        return repository.getBooks(query)
    }
}