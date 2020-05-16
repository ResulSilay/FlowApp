package app.flow.data.network.response

import app.flow.data.network.dto.response.PostResponseDto
import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("Success")
    val success: Boolean?,
    @SerializedName("StatusCode")
    val statusCode: Int?,
    @SerializedName("Data")
    val data: ArrayList<PostResponseDto>?
)