package app.flow.ui.helper.callback

import app.flow.data.network.dto.response.PostResponseDto

interface FlowCallback {
    fun onPost(postResponseDto:PostResponseDto)
    fun onPostPublish()
    fun onProfile()
    fun onRefresh()
}