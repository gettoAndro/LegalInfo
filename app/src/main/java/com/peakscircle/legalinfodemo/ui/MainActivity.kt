package com.peakscircle.legalinfodemo.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.peakscircle.legalinfo.data.dto.response.DocumentDTO
import com.peakscircle.legalinfo.ui.DocumentActivity
import com.peakscircle.legalinfo.ui.utils.ext.show
import com.peakscircle.legalinfodemo.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel by viewModel<MainViewModel>()

    private val documentResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            when(it.resultCode) {
                RESULT_OK -> Toast.makeText(this, "Document accepted successfully", Toast.LENGTH_SHORT).show()
                RESULT_CANCELED -> Toast.makeText(this, "Document cancelled", Toast.LENGTH_SHORT).show()
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
            viewModel.getDocuments(binding.editUserId.text.toString())
        }

        binding.btnGetAcceptedDocuments.setOnClickListener {
            viewModel.getAcceptedDocuments(binding.editUserId.text.toString())
        }

        viewModel.messageLive.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.loadingLive.observe(this) {
            binding.progress.show(it)
        }

        viewModel.documents.observe(this) {
            showDocumentsDialog(it)
        }

        viewModel.acceptedDocuments.observe(this) {
            showDocumentsDialog(it, false)
        }
    }

    private fun showDocumentsDialog(documents: List<DocumentDTO>, allowAccepting: Boolean = true) {
        AlertDialog.Builder(this)
            .setTitle("Documents")
            .setItems(documents.map { it.title }.toTypedArray()) { _, index ->
                val document = documents[index]
                documentResult.launch(
                    DocumentActivity.getIntent(
                        this,
                        document.title,
                        document.url,
                        allowAccepting
                    )
                )
            }
            .create()
            .show()
    }

}