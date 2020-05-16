package app.flow.util

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.IOException

object ImageUtil {

    private fun getBytesFromBitmap(bitmap: Bitmap): ByteArray? {
        try {
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream)
            return stream.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    fun getBase64Decode(encodedImage: String): Bitmap? {
        try {
            val decodedString = Base64.decode(encodedImage, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    fun getBase64Encode(bitmap: Bitmap?): String? {
        try {
            return Base64.encodeToString(bitmap?.let { getBytesFromBitmap(it) }, Base64.NO_WRAP)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    fun getMediaBitmap(file: Uri?, cr: ContentResolver): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val inputStream = file?.let { cr.openInputStream(it) }
            bitmap = BitmapFactory.decodeStream(inputStream)
            try {
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }
}