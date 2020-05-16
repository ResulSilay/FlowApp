package app.flow.data.network.interceptor

import android.content.Context
import android.util.Log
import app.flow.data.network.api.ApiSession
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        try {
            var token: String = ApiSession.getInstance(context).getToken()

            if (request.header("No-Authentication") == null) {

                val finalToken = "Bearer $token"
                request = request.newBuilder()
                    .addHeader("Authorization", finalToken)
                    .build()

                Log.d("TOKEN--> ", finalToken)
            }
        } catch (e: Exception) {
            Log.d("TIMEOUT", "true")
        }

        return chain.proceed(request)
    }
}