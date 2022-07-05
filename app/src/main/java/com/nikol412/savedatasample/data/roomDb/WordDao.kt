package com.nikol412.savedatasample.data.roomDb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.nikol412.savedatasample.data.entity.WordEntity


@Dao
interface WordDao {
    @Query("SELECT * FROM wordentity")
    fun getAll(): List<WordEntity>?

    @Query("SELECT * FROM WordEntity WHERE word LIKE :name LIMIT 1")
    fun findByName(name: String): WordEntity?

    @Insert
    fun insertAll(vararg words: WordEntity)

    @Delete
    fun delete(word: WordEntity)
}