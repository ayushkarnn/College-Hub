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

class ProjectsAimlFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_projects_aiml, container, false)

        val ds1 = view.findViewById<Button>(R.id.ds)
        val ds2 = view.findViewById<Button>(R.id.ds2)
        val ds3 = view.findViewById<Button>(R.id.ds3)
        val ds4 = view.findViewById<Button>(R.id.ds4)

        val webView = view?.findViewById<WebView>(R.id.webView)

        val url = "https://drive.google.com/file/d/1azb9bK4SGfiN9VoG97KaR0oCQWWzodor/preview"
        setupWebViewWithUrl(webView, url)
        ds1.setOnClickListener {

            val pdfUrl = "https://drive.google.com/file/d/1azb9bK4SGfiN9VoG97KaR0oCQWWzodor/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }
        ds2.setOnClickListener {
            val pdfUrl = "https://drive.google.com/file/d/1c-SQUhUi_lQDlNBA-TC1vOTL6w6Z8qmJ/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }

        ds3.setOnClickListener {
            val pdfUrl = " https://drive.google.com/file/d/1NQbgV4Wth4mkgTgHIn0T_iZnbLnuqEEI/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }
        ds4.setOnClickListener {
            val pdfUrl = "https://drive.google.com/file/d/1gWqOyMzn7jqpl5FlDP0CsoYQGRs98AKa/preview"
            setupWebViewWithUrl(webView, pdfUrl)
        }


        val networkChangeReceiver = webView?.let { NetworkChangeReceiver(it) }
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireContext().registerReceiver(networkChangeReceiver, filter)


        return view
    }
}