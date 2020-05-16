package app.flow.ui.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.flow.data.network.dto.request.PostSaveRequestDto
import app.flow.data.network.response.PostSingleResponse
import app.flow.data.network.service.PostService
import app.flow.ui.helper.callback.PublishCallback
import app.flow.ui.model.PublishModel
import app.flow.util.result.ResultCallBack
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class PublishViewModel(private val postService: PostService, private val publishCallback: PublishCallback) : ViewModel() {

    private var disposable: Disposable? = null
    private var isLoading: ObservableBoolean = ObservableBoolean(false)
    private var publistPushLiveData: MutableLiveData<ResultCallBack<PostSingleResponse>> = MutableLiveData()

    var publishModel: PublishModel = PublishModel()

    fun publishPost(postSaveRequestDto: PostSaveRequestDto): MutableLiveData<ResultCallBack<PostSingleResponse>> {
        disposable = postService.publish(postSaveRequestDto).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                onLoadingShow()
            }.doOnComplete {
            }.doOnError {
                onLoadingHide()
                publistPushLiveData.postValue(ResultCallBack.error(it.message.toString()))
            }.subscribe(
                { result ->
                    publistPushLiveData.postValue(ResultCallBack.success(result))
                },
                { e ->
                    publistPushLiveData.postValue(ResultCallBack.error(e.message.toString()))
                }
            )

        return publistPushLiveData
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

    fun onPublishClick() {
        publishCallback.onPublish()
    }

    fun onImageAddClick() {
        publishCallback.onImageAdd()
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}