package com.nikol412.savedatasample

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nikol412.savedatasample.adapter.WordAdapter
import com.nikol412.savedatasample.databinding.ActivityMainBinding
import com.nikol412.savedatasample.utils.viewBinding

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private val binding by viewBinding(ActivityMainBinding::inflate)

    private val wordAdapter by lazy { WordAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        subscribeToViewModel()
        initListeners()
    }

    private fun initViews() = with(binding) {
        rvWords.adapter = wordAdapter
    }

    private fun initListeners() {
        binding.buttonSearch.setOnClickListener {
            viewModel.onLoadWordClick(binding.etSearch.text.toString())
        }
    }

    private fun subscribeToViewModel() = with(viewModel) {
        words.observe(this@MainActivity) {
            wordAdapter.submitList(it)
        }
    }
}