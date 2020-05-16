package app.flow.data.network.service

import android.content.Context
import app.flow.data.network.api.ApiClient
import app.flow.data.network.dto.request.RateRequestDto
import app.flow.data.network.interfaces.RateInterface
import app.flow.data.network.response.RateResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.Retrofit

class RateService(context: Context) {

    private var client: Retrofit = ApiClient.getInstance(context).client()

    fun get(postId: Int): Observable<Response<RateResponse>> {
        return client.create(RateInterface::class.java).get(postId)
    }

    fun set(rateRequestDto: RateRequestDto): Observable<Response<RateResponse>> {
        return client.create(RateInterface::class.java).set(rateRequestDto)
    }
}