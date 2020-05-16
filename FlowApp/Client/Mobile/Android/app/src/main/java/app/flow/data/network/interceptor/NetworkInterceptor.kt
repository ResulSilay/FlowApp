package app.flow.data.network.interceptor

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import app.flow.data.network.api.ApiException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor(var context: Context) : Interceptor {

    var result = false;

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable()) {
            return throw ApiException("Internet bağlantısı mevcut değil.")
        }

        return chain.proceed(chain.request())
    }

    @SuppressLint("NewApi")
    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        }

        return true
    }

}