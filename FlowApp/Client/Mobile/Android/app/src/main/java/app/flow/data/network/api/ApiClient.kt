package app.flow.data.network.api

import android.content.Context
import android.util.Log
import app.flow.data.network.api.ApiConstant.BASE_HOST
import app.flow.data.network.interceptor.AuthInterceptor
import app.flow.data.network.interceptor.NetworkInterceptor
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient(private val context: Context) {

    companion object {
        private var retorfit: Retrofit? = null
        private var instance: ApiClient? = null

        fun getInstance(context: Context): ApiClient {
            if (instance == null) {
                return ApiClient(context)
            }

            return instance as ApiClient
        }
    }

    fun client(): Retrofit {
        try {
            val interceptorNetwork = NetworkInterceptor(context)
            val interceptorAuth = AuthInterceptor(context)

            val client = OkHttpClient.Builder()
                .addNetworkInterceptor(interceptorNetwork)
                .addInterceptor(interceptorAuth)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS).build()

            retorfit = Retrofit.Builder()
                .baseUrl(BASE_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(client)
                .build()

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return retorfit!!
    }
}