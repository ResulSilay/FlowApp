package app.flow.data.network.dto.request

import com.google.gson.annotations.SerializedName

class ProfilePictureRequestDto {
    @SerializedName("Img")
    var img: String? = null
}