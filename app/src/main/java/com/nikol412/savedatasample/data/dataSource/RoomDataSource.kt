package com.nikol412.savedatasample.data.dataSource

import com.nikol412.savedatasample.data.roomDb.WordDao
import com.nikol412.savedatasample.data.response.WordItemUI
import com.nikol412.savedatasample.utils.toEntity
import com.nikol412.savedatasample.utils.toUi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RoomDataSource(
    private val wordDao: WordDao,
    private val defaultDispatcher: CoroutineDispatcher
) : LocalDataSource {

    override suspend fun getWord(query: String): WordItemUI? = withContext(defaultDispatcher) {
        return@withContext wordDao.findByName(query)?.toUi()
    }

    override suspend fun getAllWords(): List<WordItemUI>? = withContext(defaultDispatcher) {
        return@withContext wordDao.getAll()?.map { it.toUi() }
    }

    override suspend fun saveWord(word: WordItemUI): Boolean = withContext(defaultDispatcher) {
        wordDao.insertAll(word.toEntity())
        return@withContext true
    }

    override suspend fun saveAllWords(words: List<WordItemUI>): Boolean =
        withContext(defaultDispatcher) {
            val list = words.map { it.toEntity() }.toTypedArray()
            wordDao.insertAll(*list)
            return@withContext true
        }
}