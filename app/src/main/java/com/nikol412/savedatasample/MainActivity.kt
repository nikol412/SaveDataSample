package com.nikol412.savedatasample

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikol412.savedatasample.adapter.WordAdapter
import com.nikol412.savedatasample.databinding.ActivityMainBinding
import com.nikol412.savedatasample.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()
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
        rvWords.layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, true)
    }

    private fun initListeners() {
        binding.buttonSearch.setOnClickListener {
            viewModel.onLoadWordClick(binding.etSearch.text.toString())
        }
        binding.chipStorageSelector.setOnCheckedStateChangeListener { group, checkedIds ->
            viewModel.onSavingTypeChangeClick(checkedIds.firstOrNull())
        }
    }

    private fun subscribeToViewModel() = with(viewModel) {
        words.observe(this@MainActivity) {
            wordAdapter.submitList(it)
        }
    }
}