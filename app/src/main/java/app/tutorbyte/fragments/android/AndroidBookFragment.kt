package app.tutorbyte.fragments.android

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.fragment.app.Fragment
import app.tutorbyte.helper.NetworkChangeReceiver
import app.tutorbyte.R
import app.tutorbyte.helper.Constants

class AndroidBookFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_android_book, container, false)

        val lc1 = view.findViewById<Button>(R.id.android1)
        val lc2 = view.findViewById<Button>(R.id.Kotlin)

        val webView = view?.findViewById<WebView>(R.id.webView)
        val pdfUrl = "https://drive.google.com/file/d/1Gq80su9nFmqnQUsuRcTG7UG1WjCzRjDw/preview"
        setupWebViewWithUrl(webView, pdfUrl)
        lc1.setOnClickListener {
            val pdfUrl = "https://drive.google.com/file/d/1Gq80su9nFmqnQUsuRcTG7UG1WjCzRjDw/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }
        lc2.setOnClickListener {
            val pdfUrl = "https://drive.google.com/file/d/15OBtsUCvLIWuHCtpE4QKOdWIFmZsYNg0/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }

        // Add a BroadcastReceiver to listen for network changes
        val networkChangeReceiver = webView?.let { NetworkChangeReceiver(it) }
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireContext().registerReceiver(networkChangeReceiver, filter)

        return view
    }
}
