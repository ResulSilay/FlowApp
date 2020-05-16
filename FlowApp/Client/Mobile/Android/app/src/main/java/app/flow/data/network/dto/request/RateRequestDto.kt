package app.flow.data.network.dto.request

import com.google.gson.annotations.SerializedName

class RateRequestDto {
    @SerializedName("postId")
    var postId: Int = 0
    @SerializedName("rate")
    var rate: Float = 0.0f
}