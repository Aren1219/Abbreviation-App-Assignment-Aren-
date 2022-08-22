package com.example.abbreviationappassignmentaren.repositories

import androidx.lifecycle.LiveData
import com.example.abbreviationappassignmentaren.database.DefinitionsEntity
import com.example.abbreviationappassignmentaren.models.DefinitionsItemModel
import com.example.abbreviationappassignmentaren.models.DefinitionsModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface Repository {

    suspend fun getDefFromApi(searchTerm: String) : Response<DefinitionsModel>

    fun getDefFromDatabase(searchTerm: String) : Flow<List<DefinitionsEntity>>

    suspend fun insertDefToDatabase(definitionsEntity: DefinitionsEntity)

}