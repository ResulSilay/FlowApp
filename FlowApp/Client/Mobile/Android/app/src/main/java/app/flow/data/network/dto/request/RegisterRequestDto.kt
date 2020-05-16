package app.flow.data.network.dto.request

import com.google.gson.annotations.SerializedName

class RegisterRequestDto {
    @SerializedName("username")
    var username: String = ""
    @SerializedName("phone")
    var phone: String = ""
    @SerializedName("email")
    var email: String = ""
    @SerializedName("password")
    var password: String = ""
    @SerializedName("address")
    var address: String = ""
}