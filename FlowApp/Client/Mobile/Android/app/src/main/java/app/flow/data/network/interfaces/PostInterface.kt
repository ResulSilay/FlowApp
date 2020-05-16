package app.flow.data.network.interfaces

import app.flow.data.network.api.ApiConstant.POSTS_GET_URI
import app.flow.data.network.api.ApiConstant.POSTS_PAGE_URI
import app.flow.data.network.api.ApiConstant.POSTS_URI
import app.flow.data.network.dto.request.PostSaveRequestDto
import app.flow.data.network.response.PostResponse
import app.flow.data.network.response.PostSingleResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PostInterface {
    @GET(POSTS_URI)
    fun getPostAll(): Observable<PostResponse>

    @GET(POSTS_PAGE_URI)
    fun getPostAll(@Path("page") pageNum: Int): Observable<PostResponse>

    @GET(POSTS_GET_URI)
    fun getPost(@Path("postId") postId: Int): Observable<PostSingleResponse>

    @POST(POSTS_URI)
    fun publishPost(@Body postSaveRequestDto: PostSaveRequestDto): Observable<PostSingleResponse>
}