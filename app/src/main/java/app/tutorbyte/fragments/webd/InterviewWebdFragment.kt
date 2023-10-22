package app.tutorbyte.fragments.webd

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

class InterviewWebdFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_interview_webd, container, false)

        val ds1 = view.findViewById<Button>(R.id.javascript)
        val ds2 = view.findViewById<Button>(R.id.react)
        val ds3 = view.findViewById<Button>(R.id.react2)
        val webView = view?.findViewById<WebView>(R.id.webView)


        val url = "https://drive.google.com/file/d/1tqduh48LytzGFaVfioALXiSo1wPEedbb/preview"
        setupWebViewWithUrl(webView, url)
        ds1.setOnClickListener {

            val pdfUrl = "https://drive.google.com/file/d/1tqduh48LytzGFaVfioALXiSo1wPEedbb/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }
        ds2.setOnClickListener {

            val pdfUrl = "https://drive.google.com/file/d/10hWThZMI8Rp8nA0LIS7LRSOFkgPoeUc3/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }
        ds3.setOnClickListener {
            val pdfUrl = "https://drive.google.com/file/d/1m7iZCekl81-nYpwSn0MczqCRuh-Fowqt/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }


        val networkChangeReceiver = webView?.let { NetworkChangeReceiver(it) }
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireContext().registerReceiver(networkChangeReceiver, filter)


        return view
    }
}
