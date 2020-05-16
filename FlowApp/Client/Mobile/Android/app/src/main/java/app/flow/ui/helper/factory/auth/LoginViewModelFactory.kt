package app.flow.ui.helper.factory.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.flow.data.network.service.AuthService
import app.flow.ui.helper.callback.auth.LoginCallback
import app.flow.ui.viewmodel.auth.LoginViewModel

class LoginViewModelFactory(private val authRepository: AuthService, private val loginCallback: LoginCallback) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java))
            return LoginViewModel(authRepository, loginCallback) as T

        throw IllegalArgumentException("Unkwown viewModel class.")
    }
}