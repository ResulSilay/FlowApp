package app.flow.data.network.dto.response

import com.google.gson.annotations.SerializedName

class RegisterResponseDto {
    @SerializedName("token")
    var token: String = ""
}