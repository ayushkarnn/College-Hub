package app.tutorbyte.helper

import android.content.Context
import android.content.SharedPreferences

class LoginHelper(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)

    var isLoggedIn: Boolean
        get() = sharedPreferences.getBoolean(IS_LOGGED_IN_KEY, false)
        set(value) = sharedPreferences.edit().putBoolean(IS_LOGGED_IN_KEY, value).apply()

    companion object {
        private const val IS_LOGGED_IN_KEY = "is_logged_in"
    }
}
