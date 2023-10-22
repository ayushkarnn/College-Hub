package com.ayush.tutoradmin

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.ayush.tutoradmin.fragments.EventFragment
import com.ayush.tutoradmin.fragments.ImportantNotificationFragment
import com.ayush.tutoradmin.fragments.JoblistFragment
import com.ayush.tutoradmin.fragments.TeacherScheduleFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : FragmentActivity() {

    private var bottomNavigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        FirebaseApp.initializeApp(this@MainActivity)

        bottomNavigationView = findViewById(R.id.bottomNav)

        if (savedInstanceState == null) {
            replaceFragment(JoblistFragment())
        }

        bottomNavigationView?.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.job -> {
                    replaceFragment(JoblistFragment())
                    true
                }
                R.id.teacherCalender -> {
                    replaceFragment(TeacherScheduleFragment())
                    true
                }
                R.id.events -> {
                    replaceFragment(EventFragment())
                    true
                }
                R.id.impNotification -> {
                    replaceFragment(ImportantNotificationFragment())
                    true
                }
                else -> false
            }
        }
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    fun showBottomNav(show: Boolean) {
        bottomNavigationView?.visibility = if (show) View.VISIBLE else View.GONE
    }
}
