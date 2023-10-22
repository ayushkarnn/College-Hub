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

class CheatWebdFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_cheat_webd, container, false)

        val ds1 = view.findViewById<Button>(R.id.html)
        val ds2 = view.findViewById<Button>(R.id.css)
        val ds3 = view.findViewById<Button>(R.id.graph)
        val ds4 = view.findViewById<Button>(R.id.Tree)
        val javascript = view.findViewById<Button>(R.id.javascript)
        val javascript2 = view.findViewById<Button>(R.id.javascript2)
        val webView = view?.findViewById<WebView>(R.id.webView)

        val url = "https://drive.google.com/file/d/15a-nx9gtHD8H6fmvGBSwxxdk3WiIXkA-/preview"
        setupWebViewWithUrl(webView, url)
        javascript.setOnClickListener {

            val pdfUrl = "https://drive.google.com/file/d/15a-nx9gtHD8H6fmvGBSwxxdk3WiIXkA-/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }
        javascript2.setOnClickListener {

            val pdfUrl = "https://drive.google.com/file/d/1ixBbLjUT_0nvM7JtDboS5iemFOTkUDf6/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }
        ds1.setOnClickListener {

            val pdfUrl = "https://drive.google.com/file/d/1MYvLRDqRfrizBpe6Bg7mQCF4eXOWpvUP/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }
        ds2.setOnClickListener {
            val pdfUrl = "https://drive.google.com/file/d/1DtNYywlgA4ykHvrHESjD-Z3Vorikt2aG/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }
        ds3.setOnClickListener {
            val pdfUrl = "https://drive.google.com/file/d/1hr8QSPXwLJXmV75igwGXttkya9BglCI6/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }
        ds4.setOnClickListener {

            val pdfUrl = "https://drive.google.com/file/d/1wEHefBcn4oHdWqZR_u0Js0KbUGKSRHVi/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }

        val networkChangeReceiver = webView?.let { NetworkChangeReceiver(it) }
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireContext().registerReceiver(networkChangeReceiver, filter)


        return view
    }
}
