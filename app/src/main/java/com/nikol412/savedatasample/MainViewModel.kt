package com.nikol412.savedatasample

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikol412.savedatasample.data.repository.DictionaryRepository
import com.nikol412.savedatasample.data.repository.SaveDestinationEnum
import com.nikol412.savedatasample.data.response.WordItemUI
import kotlinx.coroutines.launch

class MainViewModel(private val dictionaryRepository: DictionaryRepository) : ViewModel() {

    val words = MutableLiveData<List<WordItemUI>>(emptyList())

    init {
        viewModelScope.launch {
            words.postValue(dictionaryRepository.getAllLocalWords())
        }
    }

    fun onLoadWordClick(query: String?) {
        if (query.isNullOrBlank()) return
        viewModelScope.launch {
            addWord(dictionaryRepository.getWord(query))
        }
    }

    private fun addWord(word: WordItemUI) {
        words.value = (words.value ?: listOf()) + word
    }

    fun onSavingTypeChangeClick(id: Int?) {
        dictionaryRepository.selectSavingDestination(
            when (id) {
                1 -> SaveDestinationEnum.SHARED_PREFERENCES
                2 -> SaveDestinationEnum.ROOM
                3 -> SaveDestinationEnum.PROTO_DATASTORE
                4 -> SaveDestinationEnum.PREFERENCES_DATASTORE
                else -> SaveDestinationEnum.NONE
            }
        )

        viewModelScope.launch {
            words.value = dictionaryRepository.getAllLocalWords()
        }
    }
}