package app.flow.ui.viewmodel.auth

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.flow.data.network.dto.request.LoginRequestDto
import app.flow.data.network.response.LoginResponse
import app.flow.data.network.service.AuthService
import app.flow.ui.helper.callback.auth.LoginCallback
import app.flow.ui.model.UserModel
import app.flow.util.result.ResultCallBack
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginViewModel(private val authService: AuthService, private val loginCallback: LoginCallback) : ViewModel() {

    private var disposable: Disposable? = null
    private var isLoading: ObservableBoolean = ObservableBoolean(false)
    private var loginLiveData: MutableLiveData<ResultCallBack<LoginResponse>> = MutableLiveData()
    var user: UserModel = UserModel()

    fun login(loginRequestDto: LoginRequestDto): MutableLiveData<ResultCallBack<LoginResponse>> {
        disposable = authService.login(loginRequestDto).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                onLoadingShow()
            }.doOnComplete {
                onLoadingHide()
            }.doOnError {
                loginLiveData.postValue(ResultCallBack.error(it.message.toString()))
            }.subscribe(
                { result ->
                    loginLiveData.postValue(ResultCallBack.success(result))
                },
                { e ->
                    loginLiveData.postValue(ResultCallBack.error(e.message.toString()))
                }
            )

        return loginLiveData
    }

    fun isLoading(): ObservableBoolean {
        return isLoading
    }

    private fun onLoadingShow() {
        isLoading.set(true)
    }

    private fun onLoadingHide() {
        isLoading.set(false)
    }

    fun onLoginClick() {
        loginCallback.onLogin()
    }

    fun onRegisterClick() {
        loginCallback.onRegister()
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}