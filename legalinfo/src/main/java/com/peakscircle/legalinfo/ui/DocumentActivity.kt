package com.peakscircle.legalinfo.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
            setSupportActionBar(toolbar)
            setTitle(title)
            webView.loadUrl(url.orEmpty())
            btnAccept.apply {
                show(allow)

                setOnClickListener {
                    setResult(RESULT_OK)
                }
            }

            btnCancel.apply {
                show(allow)

                setOnClickListener {
                    setResult(RESULT_CANCELED)
                }
            }
        }
    }

}