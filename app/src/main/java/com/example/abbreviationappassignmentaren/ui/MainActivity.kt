package com.example.abbreviationappassignmentaren.ui

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.abbreviationappassignmentaren.adapters.ItemAdapter
import com.example.abbreviationappassignmentaren.database.DefinitionsEntity
import com.example.abbreviationappassignmentaren.databinding.ActivityMainBinding
import com.example.abbreviationappassignmentaren.models.DefinitionsModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var observer: Observer<DefinitionsEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvMain.layoutManager = LinearLayoutManager(this)

        val editText = binding.etMain.text

        observer = Observer<DefinitionsEntity>{local ->
            if (local != null) {
                Log.d("MainActivity", "Observe not empty: ${local.sf}")
                binding.rvMain.adapter = ItemAdapter(local.definitionsModel)
                binding.tvAcronym.text = local.sf
            } else {
                Log.d("MainActivity", "Observe empty")
//                viewModel.getDefFromApi(editText.toString())
//                binding.rvMain.adapter = ItemAdapter(DefinitionsModel())
                binding.tvAcronym.text = "Error"
            }
        }

        binding.btnMain.setOnClickListener{
//            viewModel.readAbbreviations.removeObservers(this@MainActivity)
            Log.d(TAG,"Btn pressed")
            viewModel.search(editText.toString())
            viewModel.readAbbreviations.observe(this@MainActivity,observer)
        }
    }
}