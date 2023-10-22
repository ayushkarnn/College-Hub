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

class NotesanFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_notesan, container, false)
        val webView = view?.findViewById<WebView>(R.id.webView)

        val url = "https://drive.google.com/file/d/1WMONJOxPloeI7EARhiISxUi8EG2u9GP9/preview"
        setupWebViewWithUrl(webView, url)
        val cwh = view.findViewById<Button>(R.id.cwh)
        val _kotlin = view.findViewById<Button>(R.id.kotlin)
        cwh.setOnClickListener {
            val url = "https://drive.google.com/file/d/1WMONJOxPloeI7EARhiISxUi8EG2u9GP9/preview"
            setupWebViewWithUrl(webView, url)
        }
        _kotlin.setOnClickListener {
            val url = "https://drive.google.com/file/d/1kcdsVagdh1D9x3IKoYdf7XrrkL_MlTbs/preview"
            setupWebViewWithUrl(webView, url)
        }

        val networkChangeReceiver = webView?.let { NetworkChangeReceiver(it) }
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireContext().registerReceiver(networkChangeReceiver, filter)



        return view
    }
}
