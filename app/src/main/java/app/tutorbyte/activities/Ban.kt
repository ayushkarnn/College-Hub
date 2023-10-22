package app.tutorbyte.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import app.tutorbyte.R
import com.airbnb.lottie.LottieAnimationView

class Ban : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ban)
        val mt = findViewById<TextView>(R.id.mt)
        val lottie =  findViewById<LottieAnimationView>(R.id.lottie)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        mt.isSelected =true
        lottie.playAnimation()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        showExitDialog()
    }
    private fun showExitDialog() {
        val builder = AlertDialog.Builder(this@Ban)
        builder.setMessage("Do you really want to exit?")
        builder.setPositiveButton("Yes") { _, _ ->
            finishAffinity()
        }
        builder.setNegativeButton("No") { _, _ ->

        }
        val dialog = builder.create()
        dialog.show()
    }
}