package app.tutorbyte.fragments.aiml

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import app.tutorbyte.helper.NetworkChangeReceiver
import app.tutorbyte.R
import app.tutorbyte.helper.Constants

class AiMlNotesFragment : Fragment() {

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebViewWithUrl(webView: WebView?, url: String) {
        webView?.let {
            it.settings.javaScriptEnabled = true
            it.settings.loadWithOverviewMode = true
            it.settings.useWideViewPort = true

            it.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    return false
                }
            }

            it.webChromeClient = object : WebChromeClient() {}

            val htmlContent = Constants.getPDFHtml(url)
            it.loadData(htmlContent, "text/html", "utf-8")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ai_ml_notes, container, false)

        val ds1 = view.findViewById<Button>(R.id.ds)
        val ds2 = view.findViewById<Button>(R.id.ds2)
        val ds3 = view.findViewById<Button>(R.id.ds3)
        val ds4 = view.findViewById<Button>(R.id.ds4)
        val ds5 = view.findViewById<Button>(R.id.ds5)
        val ds6 = view.findViewById<Button>(R.id.ds6)
        val webView = view?.findViewById<WebView>(R.id.webView)

        val url = "https://drive.google.com/file/d/1o0sm1ZRBgD9Q91VCHvdZYkYZ6hAdKQ1U/preview"
        setupWebViewWithUrl(webView, url)
        ds1.setOnClickListener {

            val pdfUrl = "https://drive.google.com/file/d/1o0sm1ZRBgD9Q91VCHvdZYkYZ6hAdKQ1U/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }
        ds2.setOnClickListener {
            val pdfUrl = "https://drive.google.com/file/d/1bInhLiKVnasSKUhZUBkOax4rQa9Ny1Xi/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }

        ds3.setOnClickListener {

            val pdfUrl = "https://drive.google.com/file/d/1ALfqzgQC9apaWWZpxN9-7Y176oQrzgTP/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }

        ds4.setOnClickListener {
            val pdfUrl = "https://drive.google.com/file/d/1JMvCq6G0z335l8fXZKHl-op7SopdqiYq/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }

        ds5.setOnClickListener {

            val pdfUrl = "https://drive.google.com/file/d/1XdI0QtDlEDch3MTWKOsMT0gvVnNmtBTn/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }

        ds6.setOnClickListener {
            val pdfUrl = "https://drive.google.com/file/d/1PqsPBD8xGwpCHA49V9DcFJmjNLG7XJfl/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }
        val networkChangeReceiver = webView?.let { NetworkChangeReceiver(it) }
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireContext().registerReceiver(networkChangeReceiver, filter)


        return view
    }
}