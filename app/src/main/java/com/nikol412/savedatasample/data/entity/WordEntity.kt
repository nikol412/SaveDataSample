package com.nikol412.savedatasample.data.entity

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikol412.savedatasample.data.response.WordItemUI
import retrofit2.http.DELETE


@Entity
data class WordEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "word") val word: String,
    @ColumnInfo(name = "phonetic") val phonetic: String?,
    @ColumnInfo(name = "origin") val origin: String?,
    @ColumnInfo(name = "meanings") val meanings: List<String>?
)

fun WordEntity.toUi() = WordItemUI(
    this.word,
    this.phonetic,
    this.origin,
    this.meanings
)

fun WordItemUI.toEntity() = WordEntity(
    0,
    this.word,
    this.phonetic,
    this.origin,
    this.meanings
)
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

@Database(entities = [WordEntity::class], version = 1)
@TypeConverters(StringConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun wordDao(): WordDao
}

class StringConverter {
    @TypeConverter
    fun restoreList(listOfString: String?): List<String?>? {
        return Gson().fromJson(listOfString, object : TypeToken<List<String?>?>() {}.type)
    }

    @TypeConverter
    fun saveList(listOfString: List<String?>?): String? {
        return Gson().toJson(listOfString)
    }
}