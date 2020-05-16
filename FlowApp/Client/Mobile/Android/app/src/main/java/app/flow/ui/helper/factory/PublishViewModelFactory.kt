package app.flow.ui.helper.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.flow.data.network.service.PostService
import app.flow.ui.helper.callback.FlowCallback
import app.flow.ui.helper.callback.PublishCallback
import app.flow.ui.viewmodel.FlowViewModel
import app.flow.ui.viewmodel.PublishViewModel

class PublishViewModelFactory(private val postService: PostService, private val publishCallback: PublishCallback) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PublishViewModel::class.java))
            return PublishViewModel(postService, publishCallback) as T

        throw IllegalArgumentException("Unkwown viewModel class.")
    }
}