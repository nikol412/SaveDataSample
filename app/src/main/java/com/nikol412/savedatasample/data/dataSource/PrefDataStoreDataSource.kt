package com.nikol412.savedatasample.data.dataSource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikol412.savedatasample.data.response.WordItemUI
import kotlinx.coroutines.flow.first

class PrefDataStoreDataSource(
    private val dataStore: DataStore<Preferences>,
    private val gsonConverter: GsonWordConverted
) : LocalDataSource {
    private val wordKey = stringPreferencesKey("word_key")

    override suspend fun saveWord(word: WordItemUI): Boolean {
        dataStore.edit {
            val currentWords =
                it[wordKey]?.let { it1 -> gsonConverter.convertFromJson(it1) } ?: arrayListOf()
            currentWords.add(word)
            it[wordKey] = gsonConverter.convertToJson(currentWords)
        }
        return true
    }

    override suspend fun saveAllWords(words: List<WordItemUI>): Boolean {
        dataStore.edit {
            val currentWords =
                it[wordKey]?.let { it1 -> gsonConverter.convertFromJson(it1) } ?: arrayListOf()
            currentWords.addAll(words)
            it[wordKey] = gsonConverter.convertToJson(currentWords)
        }
        return true
    }

    override suspend fun getWord(query: String): WordItemUI? {
        return getAllWords()?.find { it.word == query }
    }

    override suspend fun getAllWords(): List<WordItemUI>? {
        return dataStore.data.first()[wordKey]?.let { gsonConverter.convertFromJson(it) }
    }
}

class GsonWordConverted(private val gson: Gson) {

    fun <T> convertToJson(list: List<T>): String {
        return gson.toJson(list)
            ?: throw IllegalArgumentException("WordToGsonConverter: current type cannot be serialized")
    }

    fun convertFromJson(json: String): ArrayList<WordItemUI> {
        val type = object : TypeToken<ArrayList<WordItemUI>>() {}.type
        return gson.fromJson(json, type)
            ?: throw java.lang.IllegalArgumentException("WordToGsonConverter: current type cannot be deserialized")
    }

}