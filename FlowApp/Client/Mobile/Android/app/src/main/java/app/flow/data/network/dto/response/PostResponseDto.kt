package app.flow.data.network.dto.response

import com.google.gson.annotations.SerializedName

data class PostResponseDto(

    @SerializedName("PostId")
    val postId: Int = 0,
    @SerializedName("UserId")
    val userId: Int = 0,
    @SerializedName("Username")
    val username: String = "",
    @SerializedName("Address")
    val address: String = "",
    @SerializedName("Img")
    val img: String? = null,
    @SerializedName("RateValue")
    val rateValue: Float = 0f,
    @SerializedName("Description")
    val content: String? = "",
    @SerializedName("Picture")
    val picture: String? = "",
    @SerializedName("UpdatedDateTime")
    val updatedDateTime: String = "",
    @SerializedName("CreatedDateTime")
    val createdDateTime: String = "",
    @SerializedName("Status")
    val status: Boolean = false
)