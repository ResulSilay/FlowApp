package app.flow.ui.helper.factory.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.flow.data.network.service.AuthService
import app.flow.ui.helper.callback.auth.RegisterCallback
import app.flow.ui.viewmodel.auth.RegisterViewModel

class RegisterViewModelFactory(var authRepository: AuthService, private val registerCallback: RegisterCallback) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java))
            return RegisterViewModel(authRepository, registerCallback) as T

        throw IllegalArgumentException("Unkwown viewModel class.")
    }
}