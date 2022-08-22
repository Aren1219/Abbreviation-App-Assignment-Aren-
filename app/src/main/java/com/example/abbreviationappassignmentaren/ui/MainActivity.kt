package com.example.abbreviationappassignmentaren.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.abbreviationappassignmentaren.databinding.ActivityMainBinding
import com.example.abbreviationappassignmentaren.util.Resource
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
        }

        viewModel.local2.observe(this,Observer{ local ->
            when (local) {
                is Resource.Success -> {
                    binding.tvAcronym.text = local.data?.sf.toString()
                    binding.tvDefinitions.text = local.data?.lfs.toString()
                } else -> {
                    binding.tvAcronym.text = "Error"
                    binding.tvDefinitions.text = "Error"
                }
            }
        })

//        viewModel.remote.observe(this,Observer{ resource ->
//            when(resource){
//                is Resource.Success -> {
//                    binding.tvAcronym.text = viewModel.local.value?.sf.toString()
////                    binding.tvDefinitions.text = resource.data?.get(0)?.lfs.toString()
//                }
//                is Resource.Loading -> {}
//                is Resource.Error -> {}
//            }
//        })

    }
}