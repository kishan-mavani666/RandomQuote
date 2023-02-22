package com.cheezycode.randomquote.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cheezycode.randomquote.api.QuoteService
import com.cheezycode.randomquote.db.QuoteDatabase
import com.cheezycode.randomquote.models.QuoteList
import com.cheezycode.randomquote.utils.NetworkUtils

class QuoteRepository(
    private val quoteService: QuoteService,
    private val quoteDatabase: QuoteDatabase,
    private val applicationContext: Context,
) {

    private val quotesLiveData = MutableLiveData<Response<QuoteList>>()

    val quotes: LiveData<Response<QuoteList>>
        get() = quotesLiveData

    suspend fun getQuotes(page: Int) {

        if (NetworkUtils.isInternetAvailable(applicationContext)) {
            try {
                val result = quoteService.getQuotes(page)
                if (result?.body() != null) {
                    quoteDatabase.quoteDao().addQuotes(result.body()!!.results)
                    quotesLiveData.postValue(Response.Success(result.body()))
                }
            } catch (e: Exception) {
                quotesLiveData.postValue(Response.ErrorMessage(e.message.toString()))
            }
        } else {
            val quotes = quoteDatabase.quoteDao().getQuotes()
            val quoteList = QuoteList(1, 1, 1, quotes, 1, 1)
            quotesLiveData.postValue(Response.Success(quoteList))
        }

    }

    suspend fun getQuotesBackground() {
        val randomNumber = (Math.random() * 10).toInt()
        val result = quoteService.getQuotes(randomNumber)
        if (result?.body() != null) {
            quoteDatabase.quoteDao().addQuotes(result.body()!!.results)
        }
    }
}







