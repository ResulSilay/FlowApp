package app.flow.data.network.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("Success")
    val success: Boolean?,
    @SerializedName("StatusCode")
    val statusCode: Int?,
    @SerializedName("Data")
    val data: String?
)