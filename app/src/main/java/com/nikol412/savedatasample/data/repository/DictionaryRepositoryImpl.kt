package com.nikol412.savedatasample.data.repository

import com.nikol412.savedatasample.data.dataSource.LocalDataSource
import com.nikol412.savedatasample.data.dataSource.PrefDataStoreDataSource
import com.nikol412.savedatasample.data.dataSource.RetrofitDataSource
import com.nikol412.savedatasample.data.dataSource.SharedPreferencesDataSource
import com.nikol412.savedatasample.data.response.WordItemUI
import com.nikol412.savedatasample.data.response.toUi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DictionaryRepositoryImpl(
    private val defaultContext: CoroutineDispatcher,
    private val retrofitDataSource: RetrofitDataSource,
    private val sharedPrefDataSource: SharedPreferencesDataSource,
    private val prefDataStoreDataSource: PrefDataStoreDataSource
) : DictionaryRepository {
    private var currentSaveDataSource: LocalDataSource? = null

    override suspend fun getWord(query: String): WordItemUI {
        val localWord = getLocalWord(query)
        val remoteWord = loadWord(query)
        return localWord ?: remoteWord
    }

    private suspend fun getLocalWord(query: String): WordItemUI? =
        currentSaveDataSource?.getWord(query)

    private suspend fun loadWord(query: String) = withContext(defaultContext) {
        val newWord = retrofitDataSource.getWord(query).toUi()
        withContext(Dispatchers.Main) {
            currentSaveDataSource?.saveWord(newWord)
        }

        return@withContext newWord
    }

    override suspend fun getAllLocalWords(): List<WordItemUI>? {
        return currentSaveDataSource?.getAllWords()
    }

    override fun selectSavingDestination(destination: SaveDestinationEnum) {
        currentSaveDataSource = when (destination) {
            SaveDestinationEnum.ROOM -> null
            SaveDestinationEnum.SHARED_PREFERENCES -> sharedPrefDataSource
            SaveDestinationEnum.PREFERENCES_DATASTORE -> prefDataStoreDataSource
            SaveDestinationEnum.PROTO_DATASTORE -> null
            SaveDestinationEnum.NONE -> null
        }
    }
}

enum class SaveDestinationEnum {
    ROOM,
    SHARED_PREFERENCES,
    PROTO_DATASTORE,
    PREFERENCES_DATASTORE,
    NONE
}