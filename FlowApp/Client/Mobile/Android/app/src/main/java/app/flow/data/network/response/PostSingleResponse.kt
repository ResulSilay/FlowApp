package app.flow.data.network.response

import app.flow.data.network.dto.response.PostResponseDto
import com.google.gson.annotations.SerializedName

data class PostSingleResponse(
    @SerializedName("Success")
    val success: Boolean?,
    @SerializedName("StatusCode")
    val statusCode: Int?,
    @SerializedName("Data")
    val data: PostResponseDto?
)