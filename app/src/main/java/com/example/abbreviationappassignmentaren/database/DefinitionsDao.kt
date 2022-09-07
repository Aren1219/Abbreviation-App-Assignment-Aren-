package com.example.abbreviationappassignmentaren.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.abbreviationappassignmentaren.models.DefinitionsItemModel
import kotlinx.coroutines.flow.Flow

@Dao
interface DefinitionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDefinitions(definitionsEntity: DefinitionsEntity)

    @Query("SELECT * FROM Definitions WHERE sf Like :searchTerm")
    fun getSearchResult(searchTerm: String): Flow<DefinitionsEntity>
//
//    @Query("SELECT * ")

}