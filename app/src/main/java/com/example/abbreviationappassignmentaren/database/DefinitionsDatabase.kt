package com.example.abbreviationappassignmentaren.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.abbreviationappassignmentaren.models.DefinitionsItemModel

@Database(
    entities = [DefinitionsEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class DefinitionsDatabase: RoomDatabase() {

    abstract fun getDefinitionsDao(): DefinitionsDao

}