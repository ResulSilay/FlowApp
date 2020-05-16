package app.flow.data.network.service

import android.content.Context
import app.flow.data.network.api.ApiClient
import app.flow.data.network.dto.request.PostSaveRequestDto
import app.flow.data.network.interfaces.PostInterface
import app.flow.data.network.response.PostResponse
import app.flow.data.network.response.PostSingleResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit

class PostService(context: Context) {

    private var client: Retrofit = ApiClient.getInstance(context).client()

    fun all(): Observable<PostResponse> {
        return client.create(PostInterface::class.java).getPostAll()
    }

    fun all(pageNum: Int): Observable<PostResponse> {
        return client.create(PostInterface::class.java).getPostAll(pageNum)
    }

    fun get(postId: Int): Observable<PostSingleResponse> {
        return client.create(PostInterface::class.java).getPost(postId)
    }

    fun publish(postSaveRequestDto: PostSaveRequestDto): Observable<PostSingleResponse> {
        return client.create(PostInterface::class.java).publishPost(postSaveRequestDto)
    }
}