package app.flow.data.network.interfaces

import app.flow.data.network.api.ApiConstant.RATE_GET_URI
import app.flow.data.network.api.ApiConstant.RATE_URI
import app.flow.data.network.dto.request.RateRequestDto
import app.flow.data.network.response.RateResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RateInterface {
    @GET(RATE_GET_URI)
    fun get(@Path("postId") postId: Int): Observable<Response<RateResponse>>

    @POST(RATE_URI)
    fun set(@Body rateRequestDto: RateRequestDto): Observable<Response<RateResponse>>
}