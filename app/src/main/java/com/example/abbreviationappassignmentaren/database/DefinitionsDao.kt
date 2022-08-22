package com.example.abbreviationappassignmentaren.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.abbreviationappassignmentaren.models.DefinitionsItemModel

@Dao
interface DefinitionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDefinitions(definitionsItemModel: DefinitionsItemModel)

    @Query("SELECT * FROM Definitions ORDER BY sf ASC")
    fun getAllDefinitions() : LiveData<List<DefinitionsItemModel>>

    @Query("SELECT * FROM Definitions WHERE sf LIKE :searchTerm")
    fun getSearchResult(searchTerm: String): LiveData<DefinitionsItemModel>

    @Query("SELECT EXISTS(SELECT * FROM Definitions WHERE sf = :searchTerm)")
    suspend fun checkIfExists(searchTerm: String): Boolean
}