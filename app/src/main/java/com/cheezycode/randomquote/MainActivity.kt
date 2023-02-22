package com.cheezycode.randomquote

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cheezycode.randomquote.repository.Response
import com.cheezycode.randomquote.viewmodels.MainViewModel
import com.cheezycode.randomquote.viewmodels.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = (application as QuoteApplication).quoteRepository

        mainViewModel =
            ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)

        mainViewModel.quotes.observe(this, Observer { it ->
            when (it) {
                is Response.Loading -> {}
                is Response.Success -> {
                    it.data?.let {
                        Toast.makeText(this@MainActivity,
                            it.results.size.toString(),
                            Toast.LENGTH_SHORT).show()
                    }
                }
                is Response.ErrorMessage -> {
                    Toast.makeText(this@MainActivity,
                        it.errorMessage.toString(),
                        Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}