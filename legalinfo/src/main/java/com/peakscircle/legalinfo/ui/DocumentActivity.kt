package com.peakscircle.legalinfo.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.peakscircle.legalinfo.databinding.ActivityDocumentBinding
import com.peakscircle.legalinfo.ui.utils.ext.show

class DocumentActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDocumentBinding.inflate(layoutInflater) }

    companion object {
        private const val ARG_ALLOW_ACCEPTING = "argAllow"
        private const val ARG_URL = "argUrlDoc"
        private const val ARG_TITLE = "argTitle"

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

        val allow = intent.extras?.getBoolean(ARG_ALLOW_ACCEPTING) ?: true
        val url = intent.extras?.getString(ARG_URL)
        val title = intent.extras?.getString(ARG_TITLE)

        with(binding) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setTitle(title)

            webView.apply {
                webChromeClient = WebChromeClient()
                webViewClient = WebViewClient()
                loadUrl(url.orEmpty())
            }

            containerActions.show(allow)

            btnAccept.setOnClickListener {
                setResult(RESULT_OK)
                finish()
            }

            btnCancel.setOnClickListener {
                setResult(RESULT_CANCELED)
                finish()
            }
        }

        onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    finish()
                }
            })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}