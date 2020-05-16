package app.flow.data.network.service

import android.content.Context
import app.flow.data.network.api.ApiClient
import app.flow.data.network.dto.request.LoginRequestDto
import app.flow.data.network.dto.request.RegisterRequestDto
import app.flow.data.network.interfaces.AuthInterface
import app.flow.data.network.response.LoginResponse
import app.flow.data.network.response.RegisterResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit

class AuthService(context: Context)  {

    private var client: Retrofit = ApiClient.getInstance(context).client()

    fun login(loginRequestDto: LoginRequestDto): Observable<LoginResponse> {
        return  client.create(AuthInterface::class.java).login(loginRequestDto)
    }

    fun register(registerRequestDto: RegisterRequestDto): Observable<RegisterResponse> {
        return  client.create(AuthInterface::class.java).register(registerRequestDto)
    }
}