package com.example.abbreviationappassignmentaren.repositories

import androidx.lifecycle.LiveData
import com.example.abbreviationappassignmentaren.api.ApiDetails
import com.example.abbreviationappassignmentaren.database.DefinitionsDatabase
import com.example.abbreviationappassignmentaren.models.DefinitionsItemModel
import com.example.abbreviationappassignmentaren.models.DefinitionsModel
import retrofit2.Response
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    val apiDetails: ApiDetails,
    val database: DefinitionsDatabase
    ): Repository {

    override suspend fun getDefFromApi(searchTerm: String): Response<DefinitionsModel> =
        apiDetails.searchWithSf(searchTerm)

    override fun getDefFromDatabase(searchTerm: String): LiveData<DefinitionsItemModel> =
        database.getDefinitionsDao().getSearchResult(searchTerm)

    override suspend fun insertDefToDatabase(definitionsItemModel: DefinitionsItemModel) =
        database.getDefinitionsDao().insertDefinitions(definitionsItemModel)

    override suspend fun checkDataExists(searchTerm: String): Boolean =
        database.getDefinitionsDao().checkIfExists(searchTerm)

}