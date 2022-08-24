package com.example.abbreviationappassignmentaren.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.abbreviationappassignmentaren.adapters.ItemAdapter
import com.example.abbreviationappassignmentaren.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

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

        binding.btnMain.setOnClickListener{
            viewModel.readAbbreviations.removeObservers(this@MainActivity)
            viewModel.readAbbreviations.observe(this@MainActivity) { local ->
                if (local != null) {
                    Log.d("MainActivity", "Observe not empty: ${local.sf}")
                    binding.rvMain.adapter = ItemAdapter(local.definitionsModel)
                    binding.tvAcronym.text = local.sf
                } else {
                    Log.d("MainActivity", "Observe empty")
                    viewModel.getDefFromApi(editText.toString())
                    binding.tvAcronym.text = "Error"
                }
            }

            Log.d(TAG,"Btn pressed")
            viewModel.getDatabaseData(editText.toString())
        }
    }
}