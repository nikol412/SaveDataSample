package com.nikol412.savedatasample.data.roomDb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nikol412.savedatasample.data.entity.WordEntity


@Database(entities = [WordEntity::class], version = 1)
@TypeConverters(StringConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
}
