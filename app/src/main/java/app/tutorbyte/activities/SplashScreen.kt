package app.tutorbyte.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import app.tutorbyte.R
import com.airbnb.lottie.LottieAnimationView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    private lateinit var appname: TextView
    private lateinit var lottie: LottieAnimationView
    private lateinit var firebaseRemoteConfig: FirebaseRemoteConfig
    private lateinit var progressBar: ProgressBar
    private var isFetchingEnabled = true

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            if (isFetchingEnabled) {
                fetchRemoteConfig()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        progressBar = findViewById(R.id.progressBar)

        window.statusBarColor = ContextCompat.getColor(this, R.color.splashscreencolor)
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(0)
            .build()
        firebaseRemoteConfig.setConfigSettingsAsync(configSettings)
        firebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        appname = findViewById(R.id.appname)
        lottie = findViewById(R.id.lottie)
//        appname.animate().translationY(-1400f).setDuration(1500).setStartDelay(0)
//        lottie.animate().translationX(2000f).setDuration(1500).setStartDelay(1500)

        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(this, OnCompleteListener { task ->
            if (task.isSuccessful) {
                val mainActivityName = firebaseRemoteConfig.getString("MaintenaceTetra")
                updateActivityName(mainActivityName)
            } else {
                showInternetConnectionError()
                startMonitoringNetworkChanges()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        startMonitoringNetworkChanges()
    }

    override fun onPause() {
        super.onPause()
        stopMonitoringNetworkChanges()
    }

    private fun updateActivityName(mainActivityName: String) {
        launchMainActivity(mainActivityName)
    }

    private fun launchMainActivity(mainActivityName: String) {
        try {
            val mainActivityClass = Class.forName(mainActivityName)
            Handler().postDelayed({
                val intent = Intent(applicationContext, mainActivityClass)
                startActivity(intent)
                progressBar.visibility = View.GONE
            }, 2000)
        } catch (e: ClassNotFoundException) {
            showErrorToast()
            e.printStackTrace()
            progressBar.visibility = View.GONE
        }
    }

    private fun fetchRemoteConfig() {
        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(this, OnCompleteListener { task ->
            progressBar.visibility = View.VISIBLE
            if (task.isSuccessful) {
                val mainActivityName = firebaseRemoteConfig.getString("MaintenaceTetra")
                updateActivityName(mainActivityName)
            } else {
                showInternetConnectionError()
            }
        })
    }

    private fun startMonitoringNetworkChanges() {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder().build()
        cm.registerNetworkCallback(networkRequest, networkCallback)
    }

    private fun stopMonitoringNetworkChanges() {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.unregisterNetworkCallback(networkCallback)
    }

    private fun showInternetConnectionError() {
        Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_LONG).show()
    }

    private fun showErrorToast() {
        Toast.makeText(this, "Error Occurred", Toast.LENGTH_SHORT).show()
    }
}
