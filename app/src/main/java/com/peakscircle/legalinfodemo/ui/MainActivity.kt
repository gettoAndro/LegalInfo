package com.peakscircle.legalinfodemo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.peakscircle.legalinfodemo.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnConfigure.setOnClickListener {
            viewModel.configure(binding.editUrl.text.toString())
        }

        binding.btnRegister.setOnClickListener {
            viewModel.register(binding.editUserId.text.toString())
        }

        binding.btnGetDocuments.setOnClickListener {
            viewModel.getDocuments(binding.editUrl.text.toString())
        }
    }

}