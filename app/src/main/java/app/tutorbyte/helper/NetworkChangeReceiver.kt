package app.tutorbyte.helper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast

class NetworkChangeReceiver(private val webView: WebView) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo

        if (networkInfo == null || !networkInfo.isConnected) {
            webView.visibility = View.GONE
            Toast.makeText(context, "Network not connected", Toast.LENGTH_SHORT).show()
        } else {

            Toast.makeText(context, "Wait some time to load", Toast.LENGTH_SHORT).show()
            val webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    webView.visibility = View.VISIBLE

                    view?.setOnLongClickListener { true }
                    view?.isLongClickable = false
                }
                override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                    val intent2 = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent2)
                    return true
                }
            }

            webView.webViewClient = webViewClient
            webView.url?.let { webView.loadUrl(it) }
        }
    }
}
