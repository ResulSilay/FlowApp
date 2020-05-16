package app.flow.ui.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.flow.data.network.dto.request.RateRequestDto
import app.flow.data.network.response.PostSingleResponse
import app.flow.data.network.response.RateResponse
import app.flow.data.network.service.PostService
import app.flow.data.network.service.RateService
import app.flow.ui.helper.callback.PostCallback
import app.flow.ui.model.PostModel
import app.flow.ui.model.RateModel
import app.flow.util.result.ResultCallBack
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.Observables
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response

class PostViewModel(private val postService: PostService, private val rateService: RateService, private val postCallback: PostCallback) :
    ViewModel() {

    private var disposable: Disposable? = null
    private var isLoading: ObservableBoolean = ObservableBoolean(false)

    private var postGetLiveData: MutableLiveData<ResultCallBack<PostSingleResponse>> = MutableLiveData()
    private var rateGetLiveData: MutableLiveData<ResultCallBack<Response<RateResponse>>> = MutableLiveData()
    private var rateSetLiveData: MutableLiveData<ResultCallBack<Response<RateResponse>>> = MutableLiveData()

    var postModel: PostModel = PostModel()
    var rateModel: RateModel = RateModel()

    fun getPost(postId: Int): Pair<MutableLiveData<ResultCallBack<PostSingleResponse>>, MutableLiveData<ResultCallBack<Response<RateResponse>>>> {
        disposable = Observables.zip(
            postService.get(postId),
            rateService.get(postId)
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                onLoadingShow()
            }.doOnComplete {
                onLoadingHide()
            }.doOnError {
                postGetLiveData.postValue(ResultCallBack.error(it.message.toString()))
                rateGetLiveData.postValue(ResultCallBack.error(it.message.toString()))
            }.subscribe(
                { result ->
                    val postResponse = result.first
                    val rateResponse = result.second
                    postGetLiveData.postValue(ResultCallBack.success(postResponse))
                    rateGetLiveData.postValue(ResultCallBack.success(rateResponse))
                },
                { e ->
                    postGetLiveData.postValue(ResultCallBack.error(e.message.toString()))
                    rateGetLiveData.postValue(ResultCallBack.error(e.message.toString()))
                }
            )

        return Pair(postGetLiveData, rateGetLiveData)
    }

    fun rate(rateRequestDto: RateRequestDto): MutableLiveData<ResultCallBack<Response<RateResponse>>> {
        rateService.set(rateRequestDto).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                onLoadingShow()
            }.doFinally {
                onLoadingHide()
            }.doOnError {
                rateSetLiveData.postValue(ResultCallBack.error(it.message.toString()))
            }.subscribe(
                { result ->
                    rateSetLiveData.postValue(ResultCallBack.success(result))
                },
                { e ->
                    rateSetLiveData.postValue(ResultCallBack.error(e.message.toString()))
                }
            )

        return rateSetLiveData
    }

    fun onRateClick() {
        postCallback.onRate()
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

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}