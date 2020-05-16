package app.flow.util

import android.util.Patterns

object GeneralUtil {
    fun isValidEmail(email:String) = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}