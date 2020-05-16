package app.flow.ui.helper.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.flow.data.network.service.UserService
import app.flow.ui.helper.callback.ProfileEditCallback
import app.flow.ui.viewmodel.ProfileEditViewModel

class ProfileEditViewModelFactory(private val userService: UserService, private val profileEditCallback: ProfileEditCallback) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileEditViewModel::class.java))
            return ProfileEditViewModel(userService, profileEditCallback) as T

        throw IllegalArgumentException("Unkwown viewModel class.")
    }
}