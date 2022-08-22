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

            viewModel.searchDef(binding.etMain.text.toString())

            viewModel.readAbbreviations.observe(this,Observer{local ->
                if(local.isNotEmpty()){
                    binding.tvAcronym.text = local[0].sf
                    binding.tvDefinitions.text = local[0].lf
                } else {
                    viewModel.abbreviationsLiveData.observe(this,Observer{state ->
                        when(state){
                            is UiState.Success -> {
                                binding.tvAcronym.text = state.abbrevResponse[0].sf
                                binding.tvDefinitions.text = state.abbrevResponse[0].lfs.toString()
                            }
                            else -> {}
                        }
                    })

                    binding.tvAcronym.text = "Error"
                    binding.tvDefinitions.text = "Error"
                }
            })
        }



//        viewModel.local2.observe(this,Observer{ local ->
//            when (local) {
//                is Resource.Success -> {
//                    binding.tvAcronym.text = local.data?.sf.toString()
//                    binding.tvDefinitions.text = local.data?.lfs.toString()
//                } else -> {
//                    binding.tvAcronym.text = "Error"
//                    binding.tvDefinitions.text = "Error"
//                }
//            }
//        })
    }
}