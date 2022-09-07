package com.example.abbreviationappassignmentaren.ui

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.abbreviationappassignmentaren.adapters.ItemAdapter
import com.example.abbreviationappassignmentaren.database.DefinitionsEntity
import com.example.abbreviationappassignmentaren.databinding.ActivityMainBinding
import com.example.abbreviationappassignmentaren.models.DefinitionsModel
import com.example.abbreviationappassignmentaren.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvMain.layoutManager = LinearLayoutManager(this)

        val editText = binding.etMain.text

        viewModel.readAbbreviations.observe(this){local ->
            if (local != null) {
                Log.d("MainActivity", "Observe not empty: ${local.sf}")
                binding.rvMain.adapter = ItemAdapter(local.definitionsModel)
                binding.rvMain.visibility = View.VISIBLE
                binding.tvAcronym.text = local.sf
            } else {
                Log.d("MainActivity", "Observe empty")
//                viewModel.getDefFromApi(editText.toString())
//                binding.rvMain.adapter = ItemAdapter(DefinitionsModel())
                binding.tvAcronym.text = ""
                binding.rvMain.visibility = View.INVISIBLE
            }
        }
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.abbreviationsLiveData.collect(){state ->
                when (state){
                    is UiState.Error -> {
                        binding.tvAcronym.text = state.message
                    }
                    else -> {}
                }
            }
        }

        binding.btnMain.setOnClickListener{
//            viewModel.readAbbreviations.removeObservers(this@MainActivity)
            Log.d(TAG,"Btn pressed")
            viewModel.search(editText.toString())
        }
    }
}