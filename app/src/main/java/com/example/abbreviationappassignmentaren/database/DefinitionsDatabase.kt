package com.example.abbreviationappassignmentaren.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.abbreviationappassignmentaren.models.DefinitionsItemModel

@Database(
    entities = [DefinitionsItemModel::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class DefinitionsDatabase: RoomDatabase() {

    abstract fun getDefinitionsDao(): DefinitionsDao

//    companion object {
//        @Volatile
//        private var instance: DefinitionsDatabase? = null
//        private val LOCK = Any()
//
//        operator fun invoke(context: Context) = instance?: synchronized(LOCK) {
//            instance?: createDatabase(context).also { instance = it}
//        }
//
//        private fun createDatabase(context: Context) =
//            Room.databaseBuilder(
//                context.applicationContext,
//                DefinitionsDatabase::class.java,
//                "Definition.db.db"
//            ).build()
//    }
}