package com.nikol412.savedatasample.data.repository

import com.nikol412.savedatasample.data.response.WordItemUI

interface DictionaryRepository {
    suspend fun getWord(query: String): WordItemUI
    suspend fun getAllLocalWords(): List<WordItemUI>?
    fun selectSavingDestination(destination: SaveDestinationEnum)
}