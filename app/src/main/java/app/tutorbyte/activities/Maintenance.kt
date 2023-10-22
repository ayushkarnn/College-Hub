package app.tutorbyte.activities

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import app.tutorbyte.R

class Maintenance : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maintenance)
        window.statusBarColor = ContextCompat.getColor(this, R.color.lavender)
        val closeButton = findViewById<Button>(R.id.close_button)
//        val main = findViewById<TextView>(R.id.maintenance_title)
//        main.isSelected = true
        closeButton.setOnClickListener {
            showExitDialog()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        showExitDialog()
    }

    private fun showExitDialog() {
        val builder = AlertDialog.Builder(this@Maintenance)
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
