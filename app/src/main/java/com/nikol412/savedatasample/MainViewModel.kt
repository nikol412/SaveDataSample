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

    val currentWord = MutableLiveData<WordItemUI>()

    fun onLoadWordClick(query: String?) {
        if (query.isNullOrBlank()) return
        viewModelScope.launch {
            currentWord.value = dictionaryRepositoryImpl.getWord(query).firstOrNull()
        }
    }

    fun onSaveImagesClick() {
        //todo implement
    }

    fun onSavingTypeChangeClick() {
        //todo implement
    }
}