package app.flow.data.network.dto.request

import com.google.gson.annotations.SerializedName

class ProfileRequestDto {
    @SerializedName("Username")
    var name: String? = null
    @SerializedName("Email")
    var email: String? = null
    @SerializedName("Phone")
    var phone: String? = null
    @SerializedName("Address")
    var address: String? = null
}