package com.example.abbreviationappassignmentaren.repositories

import androidx.lifecycle.LiveData
import com.example.abbreviationappassignmentaren.database.DefinitionsEntity
import com.example.abbreviationappassignmentaren.models.DefinitionsItemModel
import com.example.abbreviationappassignmentaren.models.DefinitionsModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface Repository {

    suspend fun getDefFromApi(searchTerm: String) : Response<DefinitionsModel>

    suspend fun getDefFromDatabase(searchTerm: String) : DefinitionsEntity

    suspend fun insertDefToDatabase(definitionsEntity: DefinitionsEntity)

}