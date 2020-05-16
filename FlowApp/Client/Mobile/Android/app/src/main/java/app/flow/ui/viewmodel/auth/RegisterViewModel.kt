package app.flow.ui.viewmodel.auth

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.flow.data.network.dto.request.RegisterRequestDto
import app.flow.data.network.response.RegisterResponse
import app.flow.data.network.service.AuthService
import app.flow.ui.helper.callback.auth.RegisterCallback
import app.flow.ui.model.RegisterModel
import app.flow.util.result.ResultCallBack
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class RegisterViewModel(private val authService: AuthService, private val registerCallback: RegisterCallback) : ViewModel() {

    private var disposable: Disposable? = null
    private var isLoading: ObservableBoolean = ObservableBoolean(false)
    private var registerLiveData: MutableLiveData<ResultCallBack<RegisterResponse>> = MutableLiveData()

    var registerModel: RegisterModel = RegisterModel()

    fun register(registerRequestDto: RegisterRequestDto): MutableLiveData<ResultCallBack<RegisterResponse>> {
        disposable = authService.register(registerRequestDto).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                onLoadingShow()
            }.doOnComplete {
                onLoadingHide()
            }.doOnError {
                registerLiveData.postValue(ResultCallBack.error(it.message.toString()))
            }.subscribe(
                { result ->
                    registerLiveData.postValue(ResultCallBack.success(result))
                },
                { e ->
                    registerLiveData.postValue(ResultCallBack.error(e.message.toString()))
                }
            )

        return registerLiveData
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

    fun onRegisterClick() {
        registerCallback.onRegister()
    }
    
    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}