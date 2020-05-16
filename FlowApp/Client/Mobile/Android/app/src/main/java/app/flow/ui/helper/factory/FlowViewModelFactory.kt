package app.flow.ui.helper.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.flow.data.network.service.PostService
import app.flow.data.network.service.UserService
import app.flow.ui.helper.callback.FlowCallback
import app.flow.ui.viewmodel.FlowViewModel

class FlowViewModelFactory(
    private val postService: PostService,
    private val userService: UserService,
    private val postCallback: FlowCallback
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlowViewModel::class.java))
            return FlowViewModel(postService, userService, postCallback) as T

        throw IllegalArgumentException("Unkwown viewModel class.")
    }
}