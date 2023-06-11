package com.ptb.booksearch.libraraysearch.data.repository

import com.ptb.booksearch.libraraysearch.data.models.SearchResults
import com.ptb.booksearch.libraraysearch.domain.repository.LibraryRepository
import com.ptb.booksearch.network.ApiState
import com.ptb.booksearch.network.BookApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/*
* LibraryRepositoryImpl is Implementation of LibraryRepository.
* If we want to support offline first approach we can create local and remote data source classes to
* handle each case. In that scenario API call will be made from Remote Datasource layer. And offline caching
* logic will be written here in this class.
*
* */
class LibraryRepositoryImpl @Inject constructor(private val bookApi: BookApi) : LibraryRepository {
    override suspend fun getBooks(query: String): Flow<ApiState<SearchResults>> =
        flow {
            emit(ApiState.loading())
            emit(ApiState.success(bookApi.search(query)))
        }.catch {
            emit(ApiState.error(Error()))
        }
}