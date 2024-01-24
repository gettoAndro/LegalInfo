package com.peakscircle.legalinfodemo.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.peakscircle.legalinfo.ui.DocumentActivity
import com.peakscircle.legalinfodemo.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel by viewModel<MainViewModel>()

    private val documentResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                Timber.d("USER ACCEPTED DOCUMENT")
            }
        }

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

        binding.btnShowDocument.setOnClickListener {
            viewModel.getFirstDocument().onSuccess {
                documentResult.launch(DocumentActivity.getIntent(this, it.title, it.url, false))
            }
        }

        viewModel.messageLive.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

}