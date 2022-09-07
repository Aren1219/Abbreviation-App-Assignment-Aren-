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

    lateinit var readAbbreviations: LiveData<DefinitionsEntity>

    fun search(searchTerm: String) {
        if (searchTerm.isBlank()) return
        getDatabaseData(searchTerm)
        viewModelScope.launch(Dispatchers.IO) {
            if (readAbbreviations.value == null){
                getDefFromApi(searchTerm)
                getDatabaseData(searchTerm)
            }
        }
    }


    fun getDatabaseData(shortForm: String) {
        readAbbreviations = repository.getDefFromDatabase(shortForm).asLiveData()
//        Log.d(TAG,"liveData: ${readAbbreviations.value?.sf}")
    }

    private suspend fun getDefFromApi(searchTerm: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _abbreviationsLiveData.value = UiState.Loading
            val response = repository.getDefFromApi(searchTerm)
            if(response.isSuccessful){
//                Log.d(TAG,"api response success")
                if (response.body()!!.size > 0)
                    addDataToDatabase(response.body()!!)
                _abbreviationsLiveData.value = (
                    response.body()?.let {
                        UiState.Success(response.body() as DefinitionsModel)
                    }!!
                )
            } else {
                _abbreviationsLiveData.value = (
                    UiState.Error(
                        Throwable(
                            response.message()
                        )
                    )
                )
            }
        }
    }

    private fun addDataToDatabase(abbreviations: DefinitionsModel) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertDefToDatabase(DefinitionsEntity(abbreviations))
            getDatabaseData(abbreviations[0].sf)
        }
//        insertAbbreviations(abbreviationsEntity)
    }
}