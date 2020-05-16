package app.flow.ui.helper.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.flow.data.network.service.PostService
import app.flow.data.network.service.RateService
import app.flow.ui.helper.callback.FlowCallback
import app.flow.ui.helper.callback.PostCallback
import app.flow.ui.helper.callback.PublishCallback
import app.flow.ui.viewmodel.FlowViewModel
import app.flow.ui.viewmodel.PostViewModel
import app.flow.ui.viewmodel.PublishViewModel

class PostViewModelFactory(
    private val postService: PostService,
    private val rateService: RateService,
    private val postCallback: PostCallback
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java))
            return PostViewModel(postService, rateService, postCallback) as T

        throw IllegalArgumentException("Unkwown viewModel class.")
    }
}