package com.nikol412.savedatasample

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nikol412.savedatasample.databinding.ActivityMainBinding
import com.nikol412.savedatasample.utils.viewBinding

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        subscribeToViewModel()
        initListeners()
    }

    private fun initListeners() {
        binding.buttonSearch.setOnClickListener {
            viewModel.onLoadWordClick(binding.etSearch.text.toString())
        }
    }

    private fun subscribeToViewModel() = with(viewModel) {
        currentWord.observe(this@MainActivity) {
            binding.tvWord.text = "${it.word}, ${it.phonetic}"
        }
    }
}