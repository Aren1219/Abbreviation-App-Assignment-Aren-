package com.example.abbreviationappassignmentaren.repositories

import androidx.lifecycle.LiveData
import com.example.abbreviationappassignmentaren.models.DefinitionsItemModel
import com.example.abbreviationappassignmentaren.models.DefinitionsModel
import retrofit2.Response

interface Repository {

    suspend fun getDefFromApi(searchTerm: String) : Response<DefinitionsModel>

    fun getDefFromDatabase(searchTerm: String) : LiveData<DefinitionsItemModel>

    suspend fun insertDefToDatabase(definitionsItemModel: DefinitionsItemModel)

    suspend fun checkDataExists(searchTerm: String): Boolean
}