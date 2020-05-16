package app.flow.data.network.interfaces

import app.flow.data.network.api.ApiConstant.USERS_PICTURE_URI
import app.flow.data.network.api.ApiConstant.USERS_URI
import app.flow.data.network.dto.request.ProfilePasswordRequestDto
import app.flow.data.network.dto.request.ProfilePictureRequestDto
import app.flow.data.network.dto.request.ProfileRequestDto
import app.flow.data.network.response.UserResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserInterface {
    @GET(USERS_URI)
    fun get(): Observable<UserResponse>

    @PUT(USERS_URI)
    fun save(@Body profileRequestDto: ProfileRequestDto): Observable<UserResponse>

    @POST(USERS_URI)
    fun changePassword(@Body profilePasswordRequestDto: ProfilePasswordRequestDto): Observable<UserResponse>

    @POST(USERS_PICTURE_URI)
    fun changePicture(@Body profilePictureRequestDto: ProfilePictureRequestDto): Observable<UserResponse>
}