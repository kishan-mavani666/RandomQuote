package com.cheezycode.randomquote.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.cheezycode.randomquote.QuoteApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuotesWorker(private val context: Context, parameters: WorkerParameters) :
    Worker(context, parameters) {
    override fun doWork(): Result {
        Log.e("ASDFGHHHHHH", "QuotesWorker Execute")
        val repository = (context as QuoteApplication).quoteRepository
        CoroutineScope(Dispatchers.IO).launch {
            repository.getQuotesBackground()
        }
        return Result.success()
    }
}