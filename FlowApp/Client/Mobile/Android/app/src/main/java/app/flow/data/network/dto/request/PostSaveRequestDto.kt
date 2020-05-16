package app.flow.data.network.dto.request

import com.google.gson.annotations.SerializedName

class PostSaveRequestDto {
    @SerializedName("description")
    var content: String = ""
    @SerializedName("images")
    var images: List<String?> = arrayListOf()
}