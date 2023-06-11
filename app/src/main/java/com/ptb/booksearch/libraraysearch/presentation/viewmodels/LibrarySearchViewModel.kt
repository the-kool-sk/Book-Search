package com.ptb.booksearch.libraraysearch.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptb.booksearch.libraraysearch.data.models.SearchResults
import com.ptb.booksearch.libraraysearch.domain.usecases.SearchBooksUseCase
import com.ptb.booksearch.network.ApiState
import com.ptb.booksearch.network.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibrarySearchViewModel @Inject constructor(
    private val searchBooksUseCase: SearchBooksUseCase
) : ViewModel() {
    private val _searchResultsFlow = MutableStateFlow<ApiState<SearchResults>>(ApiState.init())
    val searchResultsFlow = _searchResultsFlow.asStateFlow()

    fun search(value: String) {
        Log.i(this::class.java.simpleName,value)
        viewModelScope.launch(Dispatchers.IO) {
            _searchResultsFlow.value = ApiState.loading()
            try {
                if(value.isEmpty().not()) {
                    searchBooksUseCase.invoke(value.trim())
                        .collect { state ->
                            when (state.status) {
                                Status.INIT -> {}
                                Status.LOADING -> {
                                    _searchResultsFlow.value = state
                                }
                                Status.SUCCESS -> {
                                    _searchResultsFlow.value = state
                                }
                                Status.ERROR -> {
                                    _searchResultsFlow.value = state
                                }
                            }
                        }
                }else{
                    _searchResultsFlow.value = ApiState.init()
                }
            } catch (exception: java.lang.Exception) {
                _searchResultsFlow.value = ApiState.error(error = Error(exception.message))
            }
        }
    }
}