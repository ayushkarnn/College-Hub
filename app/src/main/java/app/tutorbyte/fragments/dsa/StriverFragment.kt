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

class StriverFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_striver, container, false)

        val ds1 = view.findViewById<Button>(R.id.recursion)
        val ds2 = view.findViewById<Button>(R.id.dp)
        val ds3 = view.findViewById<Button>(R.id.graph)
        val ds4 = view.findViewById<Button>(R.id.Tree)
        val webView = view?.findViewById<WebView>(R.id.webView)
        val url = "https://drive.google.com/file/d/1eTOOv4vvpLnaW-f0DbAw0L69RN1QCauh/preview"
        setupWebViewWithUrl(webView, url)
        ds1.setOnClickListener {

            val pdfUrl = "https://drive.google.com/file/d/1eTOOv4vvpLnaW-f0DbAw0L69RN1QCauh/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }
        ds2.setOnClickListener {

            val pdfUrl = "https://drive.google.com/file/d/1gjY_zOPkFTHUeoowSC9K8zw2utXBmhQU/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }

        ds3.setOnClickListener {

            val pdfUrl = "https://drive.google.com/file/d/1n9T01ZZN-jm98cIWkZBnxCtzlgOay7Ky/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }

        ds4.setOnClickListener {
            val pdfUrl = "https://drive.google.com/file/d/1VuXTX0ctvAI1RyomHwbdQDSMP6QM9jeE/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }


        val networkChangeReceiver = webView?.let { NetworkChangeReceiver(it) }
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireContext().registerReceiver(networkChangeReceiver, filter)


        return view
    }
}
