package com.example.abbreviationappassignmentaren.ui

import android.util.Log
import androidx.lifecycle.*
import com.example.abbreviationappassignmentaren.database.DefinitionsEntity
import com.example.abbreviationappassignmentaren.models.DefinitionsModel
import com.example.abbreviationappassignmentaren.repositories.Repository
import com.example.abbreviationappassignmentaren.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository: Repository
):ViewModel() {

    private val TAG = "MainViewModel"

    private var _abbreviationsLiveData: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Loading)
    val abbreviationsLiveData: StateFlow<UiState> get() = _abbreviationsLiveData

    var readAbbreviations: MutableLiveData<DefinitionsEntity?> = MutableLiveData()

    fun search(searchTerm: String) {
        if (searchTerm.isBlank()) return
        viewModelScope.launch(Dispatchers.IO) {
            getDatabaseData(searchTerm)
            if (readAbbreviations.value == null){
                getDefFromApi(searchTerm)
            }
        }
    }


    suspend fun getDatabaseData(shortForm: String) {
        readAbbreviations.postValue(repository.getDefFromDatabase(shortForm))
//        Log.d(TAG,"liveData: ${readAbbreviations.value?.sf}")
    }

    private suspend fun getDefFromApi(searchTerm: String) {
        _abbreviationsLiveData.value = UiState.Loading
        val response = repository.getDefFromApi(searchTerm)
        if(response.isSuccessful){
            Log.d(TAG,"api response success")
            if (response.body()!!.size > 0) {
                addDataToDatabase(response.body()!!)
                _abbreviationsLiveData.value = (response.body()?.let {
                    UiState.Success(response.body() as DefinitionsModel)
                }!!)
                getDatabaseData(searchTerm)
            } else {
                _abbreviationsLiveData.value = UiState.Error("Acronym not found")
            }
        } else {
            Log.d(TAG,"api response fail")
            _abbreviationsLiveData.value = (
                UiState.Error(
                    response.message()
                )
            )
        }
    }

    private suspend fun addDataToDatabase(abbreviations: DefinitionsModel) {
        repository.insertDefToDatabase(DefinitionsEntity(abbreviations))
        getDatabaseData(abbreviations[0].sf)
//        insertAbbreviations(abbreviationsEntity)
    }
}