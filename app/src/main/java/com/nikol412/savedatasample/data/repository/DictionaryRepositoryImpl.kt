package com.nikol412.savedatasample.data.repository

import com.nikol412.savedatasample.data.dataSource.RetrofitDataSource
import com.nikol412.savedatasample.data.response.WordItemUI
import com.nikol412.savedatasample.data.response.toIU
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DictionaryRepositoryImpl(
    private val defaultContext: CoroutineDispatcher,
    private val retrofitDataSource: RetrofitDataSource
) : ImagesRepository {
    private val words: List<WordItemUI> = mutableListOf()

    suspend fun getWord(query: String): WordItemUI {
        return getLocalWord(query) ?: loadWord(query).map { it.toIU() }.first()
    }

    private fun getLocalWord(query: String): WordItemUI? {
        return words.find { it.word == query }
        //todo search for local and than remote word
    }

    private suspend fun loadWord(query: String) = withContext(defaultContext) {
        return@withContext retrofitDataSource.getWord(query)
    }

    fun getAllLocalWords() {
        //todo implement
    }

    fun selectSavingDestionation(destination: SaveDestinationEnum) {
        //todo implement
    }
}

enum class SaveDestinationEnum {
    ROOM,
    SHARED_PREFERENCES,
    PROTO_DATASTORE,
    PREFERENCES_DATASTORE
}