package app.flow.data.network.interfaces

import app.flow.data.network.api.ApiConstant.AUTH_LOGIN_URI
import app.flow.data.network.api.ApiConstant.AUTH_REGISTER_URI
import app.flow.data.network.dto.request.LoginRequestDto
import app.flow.data.network.dto.request.RegisterRequestDto
import app.flow.data.network.response.LoginResponse
import app.flow.data.network.response.RegisterResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthInterface {
    @POST(AUTH_LOGIN_URI)
    fun login(@Body userLoginRequestDto: LoginRequestDto): Observable<LoginResponse>

    @POST(AUTH_REGISTER_URI)
    fun register(@Body registerRequestDto: RegisterRequestDto): Observable<RegisterResponse>
}