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

class DsLeetCodeFragment : Fragment() {

    private lateinit var webView: WebView

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
        val view = inflater.inflate(R.layout.fragment_ds_leetcode, container, false)

        val lc1 = view.findViewById<Button>(R.id.leetcode)
        val lc2 = view.findViewById<Button>(R.id.leetcode2)
        val lc3 = view.findViewById<Button>(R.id.leetcode3)
        val lc4 = view.findViewById<Button>(R.id.leetcode4)
        val lc5 = view.findViewById<Button>(R.id.leetcode5)
        webView = view.findViewById(R.id.webView)
        val url = "https://drive.google.com/file/d/1FxJYxwtpw-a3N9hH3eOy-Nc2F5HG4akV/preview"
        setupWebViewWithUrl(webView, url)

        lc1.setOnClickListener {
            val pdfUrl = "https://drive.google.com/file/d/1FxJYxwtpw-a3N9hH3eOy-Nc2F5HG4akV/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }
        lc2.setOnClickListener {
            val pdfUrl = "https://drive.google.com/file/d/1rPR_wupbnMcwlUB1WVHu9APtlPCBIOq3/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }

        lc3.setOnClickListener {
            val pdfUrl = "https://drive.google.com/file/d/18SXY8QwyLX8Q-IGgaCHfMlYnJ-a1vY64/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }

        lc4.setOnClickListener {
            val pdfUrl = "https://drive.google.com/file/d/1crhfxoMSTe3avuzQ6Ta3eofYoiq_Y3Ei/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }

        lc5.setOnClickListener {
            val pdfUrl = "https://drive.google.com/file/d/17Z8Lz_SRggcLH2IsM2nff_f1m9GlYNDL/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }

        // Add a BroadcastReceiver to listen for network changes
        val networkChangeReceiver = NetworkChangeReceiver(webView)
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireContext().registerReceiver(networkChangeReceiver, filter)

        return view
    }
}
