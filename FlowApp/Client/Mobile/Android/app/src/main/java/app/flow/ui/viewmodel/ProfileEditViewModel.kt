package app.flow.ui.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.flow.data.network.dto.request.ProfilePasswordRequestDto
import app.flow.data.network.dto.request.ProfilePictureRequestDto
import app.flow.data.network.dto.request.ProfileRequestDto
import app.flow.data.network.response.UserResponse
import app.flow.data.network.service.UserService
import app.flow.ui.helper.callback.ProfileEditCallback
import app.flow.ui.model.UserModel
import app.flow.util.result.ResultCallBack
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ProfileEditViewModel(private val userService: UserService, private val profileEditCallback: ProfileEditCallback) : ViewModel() {

    private var disposable: Disposable? = null
    private var isLoading: ObservableBoolean = ObservableBoolean(false)
    var profileModel: UserModel = UserModel()

    private var profileGetLiveData: MutableLiveData<ResultCallBack<UserResponse>> = MutableLiveData()
    private var profileSaveLiveData: MutableLiveData<ResultCallBack<UserResponse>> = MutableLiveData()
    private var profilePasswordLiveData: MutableLiveData<ResultCallBack<UserResponse>> = MutableLiveData()
    private var profilePictureLiveData: MutableLiveData<ResultCallBack<UserResponse>> = MutableLiveData()

    fun get(): MutableLiveData<ResultCallBack<UserResponse>> {
        disposable = userService.get().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                onLoadingShow()
            }.doFinally {
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

    fun save(profileRequestDto: ProfileRequestDto): MutableLiveData<ResultCallBack<UserResponse>> {
        disposable = userService.save(profileRequestDto).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                onLoadingShow()
            }.doFinally {
                onLoadingHide()
            }.doOnError {
                profileSaveLiveData.postValue(ResultCallBack.error(it.message.toString()))
            }.subscribe(
                { result ->
                    profileSaveLiveData.postValue(ResultCallBack.success(result))
                },
                { e ->
                    profileSaveLiveData.postValue(ResultCallBack.error(e.message.toString()))
                }
            )

        return profileSaveLiveData
    }

    fun changePassword(profilePasswordRequestDto: ProfilePasswordRequestDto): MutableLiveData<ResultCallBack<UserResponse>> {
        disposable = userService.changePassword(profilePasswordRequestDto).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                onLoadingShow()
            }.doFinally {
                onLoadingHide()
            }.doOnError {
                profilePasswordLiveData.postValue(ResultCallBack.error(it.message.toString()))
            }.subscribe(
                { result ->
                    profilePasswordLiveData.postValue(ResultCallBack.success(result))
                },
                { e ->
                    profilePasswordLiveData.postValue(ResultCallBack.error(e.message.toString()))
                }
            )

        return profilePasswordLiveData
    }

    fun changPicture(profilePictureRequestDto: ProfilePictureRequestDto): MutableLiveData<ResultCallBack<UserResponse>> {
        disposable = userService.changePicture(profilePictureRequestDto).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                onLoadingShow()
            }.doOnComplete {
                onLoadingHide()
            }.doOnError {
                profilePictureLiveData.postValue(ResultCallBack.error(it.message.toString()))
            }.subscribe(
                { result ->
                    profilePictureLiveData.postValue(ResultCallBack.success(result))
                },
                { e ->
                    profilePictureLiveData.postValue(ResultCallBack.error(e.message.toString()))
                }
            )

        return profilePictureLiveData
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

    fun onInfoSaveClick() {
        profileEditCallback.onChangeInfo()
    }

    fun onPasswordSaveClick() {
        profileEditCallback.onChangePassword()
    }

    fun onImageClick() {
        profileEditCallback.onChangeImage()
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}