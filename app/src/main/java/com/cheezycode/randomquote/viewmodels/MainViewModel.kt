package com.cheezycode.randomquote.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheezycode.randomquote.models.QuoteList
import com.cheezycode.randomquote.repository.QuoteRepository
import com.cheezycode.randomquote.repository.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: QuoteRepository) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getQuotes(1)
        }
    }

    val quotes: LiveData<Response<QuoteList>>
        get() = repository.quotes
}