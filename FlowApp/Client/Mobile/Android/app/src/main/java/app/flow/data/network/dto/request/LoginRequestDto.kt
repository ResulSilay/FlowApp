package app.flow.data.network.dto.request

import com.google.gson.annotations.SerializedName

class LoginRequestDto {
    @SerializedName("email")
    var email: String = ""
    @SerializedName("password")
    var password: String = ""
}