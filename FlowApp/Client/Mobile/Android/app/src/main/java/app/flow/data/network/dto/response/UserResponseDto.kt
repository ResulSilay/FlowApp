package app.flow.data.network.dto.response

import com.google.gson.annotations.SerializedName

class UserResponseDto {
    @SerializedName("Img")
    var img: String? = null
    @SerializedName("Username")
    var username: String? = null
    @SerializedName("Email")
    var email: String? = null
    @SerializedName("Phone")
    var phone: String? = null
    @SerializedName("Address")
    var address: String? = null
}