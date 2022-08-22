package com.example.abbreviationappassignmentaren.ui

import androidx.lifecycle.*
import com.example.abbreviationappassignmentaren.database.DefinitionsEntity
import com.example.abbreviationappassignmentaren.models.DefinitionsItemModel
import com.example.abbreviationappassignmentaren.models.DefinitionsModel
import com.example.abbreviationappassignmentaren.repositories.Repository
import com.example.abbreviationappassignmentaren.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository: Repository
):ViewModel() {

    private var _abbreviationsLiveData: MutableLiveData<UiState> =
        MutableLiveData(UiState.Loading)
    val abbreviationsLiveData: LiveData<UiState> get() = _abbreviationsLiveData

    lateinit var readAbbreviations: LiveData<List<DefinitionsEntity>>

    private fun readAbbreviations(shortForm: String) {
        readAbbreviations = repository.getDefFromDatabase(shortForm).asLiveData()
    }

    fun searchDef(searchTerm: String) {
        getDefFromApi(searchTerm)
        readAbbreviations(searchTerm)
//        if (readAbbreviations.value?.isEmpty() == true){
//            getDefFromApi(searchTerm)
//        }
    }

    private fun getDefFromDatabase(searchTerm: String) {
        readAbbreviations = repository.getDefFromDatabase(searchTerm).asLiveData()
    }

    private fun getDefFromApi(searchTerm: String) =
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getDefFromApi(searchTerm)
            if(response.isSuccessful){
                _abbreviationsLiveData.postValue(
                    response.body()?.let {
                        if (response.body()!!.size > 0) addDataToDatabase(it)
                        UiState.Success(
                            response.body() as DefinitionsModel
                        )
                    }
                )
            } else {
                _abbreviationsLiveData.postValue(
                    UiState.Error(
                        Throwable(
                            response.message()
                        )
                    )
                )
            }
        }
    private fun addDataToDatabase(abbreviations: DefinitionsModel) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertDefToDatabase(DefinitionsEntity(abbreviations))
        }
//        insertAbbreviations(abbreviationsEntity)
    }
}