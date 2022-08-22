package com.example.abbreviationappassignmentaren.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.abbreviationappassignmentaren.databinding.ActivityMainBinding
import com.example.abbreviationappassignmentaren.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMain.setOnClickListener{

            viewModel.readAbbreviations.removeObservers(this@MainActivity)

            viewModel.readAbbreviations(binding.etMain.text.toString())
            
            viewModel.readAbbreviations.observe(this@MainActivity,Observer { local ->
                if (local.isNotEmpty()) {
                    binding.tvAcronym.text = local[0].sf
                    binding.tvDefinitions.text = local[0].lf
                } else {
                    viewModel.getDefFromApi(binding.etMain.text.toString())
                    binding.tvAcronym.text = ""
                    binding.tvDefinitions.text = ""
                }
            })

            if (binding.etMain.text.isNotBlank())
                viewModel.readAbbreviations(binding.etMain.text.toString())
        }
    }
}