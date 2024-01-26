package com.peakscircle.legalinfo.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.peakscircle.legalinfo.databinding.ActivityDocumentBinding
import com.peakscircle.legalinfo.domain.NetworkResult
import com.peakscircle.legalinfo.ui.utils.ext.show

class DocumentActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDocumentBinding.inflate(layoutInflater) }
    private val viewModel: DocumentViewModel by viewModels { DocumentViewModel.Factory }

    private var allowAccepting = true

    companion object {
        private const val ARG_ALLOW_ACCEPTING = "argAllow"
        private const val ARG_URL = "argUrlDoc"
        private const val ARG_TITLE = "argTitle"

        const val RESULT_REVIEWED = 102

        fun getIntent(
            context: Context,
            title: String,
            documentUrl: String,
            allowAccepting: Boolean = true
        ) = Intent(context, DocumentActivity::class.java).apply {
            putExtra(ARG_TITLE, title)
            putExtra(ARG_ALLOW_ACCEPTING, allowAccepting)
            putExtra(ARG_URL, documentUrl)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        allowAccepting = intent.extras?.getBoolean(ARG_ALLOW_ACCEPTING) ?: true
        val url = intent.extras?.getString(ARG_URL)
        val title = intent.extras?.getString(ARG_TITLE)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle(title)

        initViews(url.orEmpty())

        viewModel.loadingLive.observe(this) {
            binding.progress.show(it)
        }

        onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    close()
                }
            })
    }

    private fun initViews(url: String) {
        with(binding) {

            webView.apply {
                webChromeClient = WebChromeClient()
                webViewClient = WebViewClient()
                loadUrl(url)
            }

            containerActions.show(allowAccepting)

            btnAccept.setOnClickListener {
                viewModel.acceptDocument {
                    when (it) {
                        is NetworkResult.Success -> {
                            setResult(RESULT_OK)
                            finish()
                        }

                        is NetworkResult.Error -> {
                            Toast.makeText(
                                applicationContext,
                                "Something went wrong",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

            btnCancel.setOnClickListener {
                setResult(RESULT_CANCELED)
                finish()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        close()
        return true
    }

    private fun close() {
        if (allowAccepting) {
            setResult(RESULT_CANCELED)
            finish()
        } else {
            setResult(RESULT_REVIEWED)
            finish()
        }
    }

}