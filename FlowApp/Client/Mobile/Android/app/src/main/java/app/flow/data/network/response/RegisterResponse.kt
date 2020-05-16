package app.flow.data.network.response

import app.flow.data.network.dto.response.UserResponseDto
import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("Success")
    val success: Boolean?,
    @SerializedName("StatusCode")
    val statusCode: Int?,
    @SerializedName("Data")
    val data: UserResponseDto?
)