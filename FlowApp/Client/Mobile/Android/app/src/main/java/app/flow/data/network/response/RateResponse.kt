package app.flow.data.network.response

import app.flow.data.network.dto.response.RateResponseDto
import com.google.gson.annotations.SerializedName

data class RateResponse(
    @SerializedName("Success")
    val success: Boolean?,
    @SerializedName("StatusCode")
    val statusCode: Int?,
    @SerializedName("Data")
    val data: RateResponseDto?
)