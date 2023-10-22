package app.tutorbyte.fragments.dsa

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

class OtherFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_other, container, false)
        val dsaJava = view.findViewById<Button>(R.id.dsa_java)
        val dsa = view.findViewById<Button>(R.id.dsa)
        val cpp = view.findViewById<Button>(R.id.cpp)
        val webView = view?.findViewById<WebView>(R.id.webView)
        val url = "https://drive.google.com/file/d/1dlN9y1V01QWphZaQjvNvFKMEIgtAptEO/preview"
        setupWebViewWithUrl(webView, url)

        dsaJava.setOnClickListener {
            val pdfUrl = "https://drive.google.com/file/d/1dlN9y1V01QWphZaQjvNvFKMEIgtAptEO/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }

        dsa.setOnClickListener {
            val pdfUrl = "https://drive.google.com/file/d/1P2E0a7xJ-lEkty2WaupQTvTjSbQKXI6j/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }

        cpp.setOnClickListener {
            val pdfUrl = "https://drive.google.com/file/d/1hlVbkCCBrlT-_naL8PL06hJhgwvT3Idm/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }

        // Add a BroadcastReceiver to listen for network changes
        val networkChangeReceiver = webView?.let { NetworkChangeReceiver(it) }
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireContext().registerReceiver(networkChangeReceiver, filter)



        return view
    }
}
