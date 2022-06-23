package com.nikol412.savedatasample.data.dataSource

import com.nikol412.savedatasample.data.response.WordItemUI

interface LocalDataSource {
    suspend fun getAllWords(): List<WordItemUI>?

    suspend fun getWord(query: String): WordItemUI?

    suspend fun saveWord(word: WordItemUI): Boolean

    suspend fun saveAllWords(words: List<WordItemUI>): Boolean
}