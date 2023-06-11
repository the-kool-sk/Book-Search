package com.ptb.booksearch.libraraysearch.di

import com.ptb.booksearch.DependencyGraph
import com.ptb.booksearch.libraraysearch.data.repository.LibraryRepositoryImpl
import com.ptb.booksearch.libraraysearch.domain.repository.LibraryRepository
import com.ptb.booksearch.libraraysearch.domain.usecases.SearchBooksUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LibraryModule {
    @Provides
    fun provideLibrarySearchUseCase(repository: LibraryRepository): SearchBooksUseCase {
        return SearchBooksUseCase(repository)
    }

    @Provides
    fun provideLibraryRepository(): LibraryRepository {
        val booksApi =  DependencyGraph.bookApi
        return LibraryRepositoryImpl(booksApi)
    }
}