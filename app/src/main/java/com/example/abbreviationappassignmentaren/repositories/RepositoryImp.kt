package com.example.abbreviationappassignmentaren.repositories

import androidx.lifecycle.LiveData
import com.example.abbreviationappassignmentaren.api.ApiDetails
import com.example.abbreviationappassignmentaren.database.DefinitionsDao
import com.example.abbreviationappassignmentaren.database.DefinitionsDatabase
import com.example.abbreviationappassignmentaren.database.DefinitionsEntity
import com.example.abbreviationappassignmentaren.models.DefinitionsItemModel
import com.example.abbreviationappassignmentaren.models.DefinitionsModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    val apiDetails: ApiDetails,
    val dao: DefinitionsDao
    ): Repository {

    override suspend fun getDefFromApi(searchTerm: String): Response<DefinitionsModel> =
        apiDetails.searchWithSf(searchTerm)

    override suspend fun getDefFromDatabase(searchTerm: String): DefinitionsEntity =
        dao.getSearchResult(searchTerm)

    override suspend fun insertDefToDatabase(definitionsEntity: DefinitionsEntity) =
        dao.insertDefinitions(definitionsEntity)


}