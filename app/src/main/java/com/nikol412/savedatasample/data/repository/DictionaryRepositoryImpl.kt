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
    suspend fun getWord(query: String): List<WordItemUI> {
        return loadWord(query).map { it.toIU() }
        //todo search for local and than remote word
    }

    private suspend fun loadWord(query: String) = withContext(defaultContext) {
        return@withContext retrofitDataSource.getWord(query)
    }

    fun getAllLocalWords() {
        //todo implement
    }
}