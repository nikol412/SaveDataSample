package com.nikol412.savedatasample

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikol412.savedatasample.data.dataSource.Retrofit
import com.nikol412.savedatasample.data.repository.DictionaryRepositoryImpl
import com.nikol412.savedatasample.data.response.WordItemUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val dictionaryRepositoryImpl =
        DictionaryRepositoryImpl(Dispatchers.IO, Retrofit.getRetrofit())

    val words = MutableLiveData<List<WordItemUI>>(emptyList())

    fun onLoadWordClick(query: String?) {
        if (query.isNullOrBlank()) return
        viewModelScope.launch {
            dictionaryRepositoryImpl.getWord(query).firstOrNull()?.let { addWord(it) }
        }
    }

    private fun addWord(word: WordItemUI) {
        words.value = words.value!! + word
    }

//    fun onSaveImagesClick() {
//        //todo implement
//    }

    fun onSavingTypeChangeClick() {
        //todo implement
    }
}