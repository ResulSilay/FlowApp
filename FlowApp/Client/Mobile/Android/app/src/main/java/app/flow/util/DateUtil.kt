package app.flow.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

object DateUtil {

    @SuppressLint("SimpleDateFormat")
    fun getDateFormat(dateTime: String): String {
        try {
            val outputFormat = SimpleDateFormat("dd MMM yyyy EEEE");
            val inputFormat = SimpleDateFormat("yyyy-MM-dd");

            val date = inputFormat.parse(dateTime);
            return outputFormat.format(date);
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }
}