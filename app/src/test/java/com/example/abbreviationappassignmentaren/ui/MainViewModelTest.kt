package com.example.abbreviationappassignmentaren.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.example.abbreviationappassignmentaren.database.DefinitionsEntity
import com.example.abbreviationappassignmentaren.models.DefinitionsItemModel
import com.example.abbreviationappassignmentaren.models.DefinitionsModel
import com.example.abbreviationappassignmentaren.models.LfModel
import com.example.abbreviationappassignmentaren.models.VarModel
import com.example.abbreviationappassignmentaren.repositories.Repository
import com.example.abbreviationappassignmentaren.util.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class MainViewModelTest{

    private val testDispatcher = TestCoroutineDispatcher()
    @get:Rule
    val instantTaskExecutionRule: TestRule = InstantTaskExecutorRule()

    lateinit var viewModel: MainViewModel

    @Mock
    lateinit var repository: Repository

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(repository)
    }

    @After
    fun cleanUp(){
        Dispatchers.resetMain()
    }

    @Test
    fun `test empty Api Response`() {
        runBlocking {
            Mockito.`when`(repository.getDefFromApi("AA"))
                .thenReturn(Response.success(DefinitionsModel()))

            repository.getDefFromApi("AA")
            viewModel.abbreviationsLiveData.asLiveData().observeForever{
                assertEquals(DefinitionsModel(),(it as UiState.Success))
            }
        }
    }

    @Test
    fun `test empty database`() {
        runBlocking {
            Mockito.`when`(repository.getDefFromDatabase("AA"))
                .thenReturn(getRoomData())
            viewModel.getDatabaseData("AA")
            viewModel.readAbbreviations.observeForever{
                assertEquals(getRoomData().asLiveData(),it)
            }
        }
    }

    private fun getRoomData(): Flow<DefinitionsEntity> =
        MutableStateFlow(DefinitionsEntity(getDefinitionsModel()))

    private fun getDefinitionsModel(): DefinitionsModel {
        val definitionsModel = DefinitionsModel()
        definitionsModel.add(DefinitionsItemModel(
            listOf(LfModel(0,"",0, listOf(VarModel(0,"",0)))),
            ""
        ))
        return definitionsModel
    }

}