package app.flow.data.network.api

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast

class ApiSession(val context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences("app.flow.auth", Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = preferences.edit()

    companion object {
        private var instance: ApiSession? = null
        @Synchronized
        fun getInstance(context: Context): ApiSession {
            if (instance != null) {
                return instance as ApiSession
            }

            instance = ApiSession(context)
            return instance as ApiSession
        }
    }

    private fun setString(name: String, value: String) {
        editor.putString(name, value)
        editor.apply()
    }

    private fun setInt(name: String, value: Int) {
        editor.putInt(name, value)
        editor.apply()
    }

    private fun getString(name: String): String? {
        return preferences.getString(name, "none")
    }

    private fun getInt(name: String): Int {
        return preferences.getInt(name, 0)
    }

    fun setToken(token: String) {
        setString("token", token)
    }

    fun getToken(): String {
        return getString("token").toString()
    }

    fun isAccount(): Boolean {
        if (getToken().isNotEmpty() && getToken() != "none")
            return true

        return false
    }

    fun logout() {
        clear()
    }

    private fun clear() {
        setInt("id", 0)
        setString("token", "")
    }
}