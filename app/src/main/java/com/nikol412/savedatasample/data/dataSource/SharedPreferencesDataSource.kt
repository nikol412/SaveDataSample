package com.nikol412.savedatasample.data.dataSource

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikol412.savedatasample.data.response.WordItemUI

class SharedPreferencesDataSource(
    private val sharedPref: SharedPreferences,
    private val gson: Gson
) : LocalDataSource {
    companion object {
        const val SHARED_PREF_DEFAULT = "shared_pref_default"
    }

    private val listTag = "list_tag_shared_preferences"

    override suspend fun getAllWords(): List<WordItemUI>? {
        return getList()
    }

    override suspend fun getWord(query: String): WordItemUI? {
        return getList()?.find { it.word == query }
    }

    override suspend fun saveAllWords(words: List<WordItemUI>): Boolean {
        return try {
            setList(ArrayList(words))
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun saveWord(word: WordItemUI): Boolean {
        val list = getList() ?: arrayListOf()
        list.add(word)

        return catchException {
            setList(list)
        }
    }

    private fun catchException(action: () -> Unit): Boolean =
        try {
            action()
            true
        } catch (e: Exception) {
            false
        }

    private fun setList(list: ArrayList<WordItemUI>) {
        val json = gson.toJson(list)
        with(sharedPref.edit()) {
            putString(listTag, json)
            commit()
        }
    }

    private fun getList(): ArrayList<WordItemUI>? {
        val json = sharedPref.getString(listTag, null)
        val type = object : TypeToken<ArrayList<WordItemUI>>() {}.type
        return gson.fromJson(json, type)
    }
}