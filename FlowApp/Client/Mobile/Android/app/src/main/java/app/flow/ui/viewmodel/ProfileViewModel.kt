package app.flow.ui.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.flow.data.network.response.UserResponse
import app.flow.data.network.service.UserService
import app.flow.ui.helper.callback.ProfileCallback
import app.flow.ui.model.UserModel
import app.flow.util.result.ResultCallBack
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ProfileViewModel(private val userService: UserService, private val profileCallback: ProfileCallback) : ViewModel() {

    private var disposable: Disposable? = null
    private var isLoading: ObservableBoolean = ObservableBoolean(false)
    private var profileGetLiveData: MutableLiveData<ResultCallBack<UserResponse>> = MutableLiveData()

    var profileModel: UserModel = UserModel()

    fun get(): MutableLiveData<ResultCallBack<UserResponse>> {
        disposable = userService.get().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                onLoadingShow()
            }.doOnComplete {
                onLoadingHide()
            }.doOnError {
                profileGetLiveData.postValue(ResultCallBack.error(it.message.toString()))
            }.subscribe(
                { result ->
                    profileGetLiveData.postValue(ResultCallBack.success(result))
                },
                { e ->
                    profileGetLiveData.postValue(ResultCallBack.error(e.message.toString()))
                }
            )

        return profileGetLiveData
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

    fun onEditClick() {
        profileCallback.onEdit()
    }

    fun onLogoutClick() {
        profileCallback.onLogout()
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}