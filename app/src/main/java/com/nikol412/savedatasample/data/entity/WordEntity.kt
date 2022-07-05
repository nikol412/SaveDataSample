package com.nikol412.savedatasample.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class WordEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "word") val word: String,
    @ColumnInfo(name = "phonetic") val phonetic: String?,
    @ColumnInfo(name = "origin") val origin: String?,
    @ColumnInfo(name = "meanings") val meanings: List<String>?
)
