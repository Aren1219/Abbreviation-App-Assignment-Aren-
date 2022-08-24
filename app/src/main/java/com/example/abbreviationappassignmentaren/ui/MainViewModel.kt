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

    var readAbbreviations: LiveData<DefinitionsEntity> = MutableLiveData()

    var job:Job? = null

    fun getDatabaseData(shortForm: String) {
        if (shortForm.isNotBlank())
            readAbbreviations = repository.getDefFromDatabase(shortForm.uppercase()).asLiveData()
//        Log.d(TAG,"liveData: ${readAbbreviations.value?.sf}")
    }

    fun getDefFromApi(searchTerm: String) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
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
//                readAbbreviations(searchTerm)
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
        job?.cancel()
        job = CoroutineScope(Dispatchers.IO).launch {
            repository.insertDefToDatabase(DefinitionsEntity(abbreviations))
            getDatabaseData(abbreviations[0].sf)
        }
//        insertAbbreviations(abbreviationsEntity)
    }
}