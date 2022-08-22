package com.example.abbreviationappassignmentaren.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abbreviationappassignmentaren.models.DefinitionsItemModel
import com.example.abbreviationappassignmentaren.models.DefinitionsModel
import com.example.abbreviationappassignmentaren.repositories.Repository
import com.example.abbreviationappassignmentaren.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository: Repository
):ViewModel() {

    val remote: MutableLiveData<Resource<DefinitionsModel>> = MutableLiveData()

    var local: LiveData<DefinitionsItemModel> = MutableLiveData()
    var local2: MutableLiveData<Resource<DefinitionsItemModel>> = MutableLiveData()

    fun searchDef(searchTerm: String) {
        getDefFromDatabase(searchTerm)
        local2.postValue(handleLocalResponse(local))
        if (local2.value is Resource.Error) {
            getDefFromApi(searchTerm)
            local2.postValue(handleLocalResponse(local))
        }
    }

    private fun getDefFromApi(searchTerm: String) =
        viewModelScope.launch(Dispatchers.IO) {
            remote.postValue(Resource.Loading())
            val response = repository.getDefFromApi(searchTerm)
            remote.postValue(handleRemoteResponse(response))
        }

    private fun handleRemoteResponse(response: Response<DefinitionsModel>): Resource<DefinitionsModel>{
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                insertDefToDatabase(resultResponse[0])
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleLocalResponse(data: LiveData<DefinitionsItemModel>): Resource<DefinitionsItemModel>{
        if (!data.value?.sf.isNullOrEmpty()){
            data.value?. let { result ->
                return (Resource.Success(result))
            }
        }
        return (Resource.Error("No local data found"))
    }

    private fun insertDefToDatabase(definitionsItemModel: DefinitionsItemModel) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertDefToDatabase(definitionsItemModel)
            getDefFromDatabase(definitionsItemModel.sf)
        }

    private fun getDefFromDatabase(searchTerm: String) {
        local = repository.getDefFromDatabase(searchTerm)
    }

}