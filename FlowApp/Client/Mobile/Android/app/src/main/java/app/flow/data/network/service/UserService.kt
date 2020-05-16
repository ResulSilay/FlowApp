package app.flow.data.network.service

import android.content.Context
import app.flow.data.network.api.ApiClient
import app.flow.data.network.dto.request.ProfilePasswordRequestDto
import app.flow.data.network.dto.request.ProfilePictureRequestDto
import app.flow.data.network.dto.request.ProfileRequestDto
import app.flow.data.network.interfaces.UserInterface
import app.flow.data.network.response.UserResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit

class UserService(var context: Context) {

    private var client: Retrofit = ApiClient.getInstance(context).client()

    fun get(): Observable<UserResponse> {
        return client.create(UserInterface::class.java).get()
    }

    fun save(profileRequestDto: ProfileRequestDto): Observable<UserResponse> {
        return client.create(UserInterface::class.java).save(profileRequestDto)
    }

    fun changePassword(profilePasswordRequestDto: ProfilePasswordRequestDto): Observable<UserResponse> {
        return client.create(UserInterface::class.java).changePassword(profilePasswordRequestDto)
    }

    fun changePicture(profilePictureRequestDto: ProfilePictureRequestDto): Observable<UserResponse> {
        return client.create(UserInterface::class.java).changePicture(profilePictureRequestDto)
    }
}