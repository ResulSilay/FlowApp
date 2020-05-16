package app.flow.data.network.dto.request

import com.google.gson.annotations.SerializedName

class ProfilePasswordRequestDto {
    @SerializedName("Password")
    var password: String? = null
}