package app.flow.ui.helper.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.flow.data.network.service.UserService
import app.flow.ui.helper.callback.ProfileCallback
import app.flow.ui.viewmodel.ProfileViewModel

class ProfileViewModelFactory(private val userService: UserService, private val profileCallback: ProfileCallback) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java))
            return ProfileViewModel(userService, profileCallback) as T

        throw IllegalArgumentException("Unkwown viewModel class.")
    }
}