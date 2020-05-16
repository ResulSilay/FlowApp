package app.flow.ui.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.flow.data.network.dto.response.PostResponseDto
import app.flow.data.network.response.PostResponse
import app.flow.data.network.response.UserResponse
import app.flow.data.network.service.PostService
import app.flow.data.network.service.UserService
import app.flow.ui.helper.adapter.PostAdapter
import app.flow.ui.helper.callback.FlowCallback
import app.flow.ui.model.UserModel
import app.flow.util.helper.SingleLiveEvent
import app.flow.util.result.ResultCallBack
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.Observables
import io.reactivex.rxjava3.schedulers.Schedulers

class FlowViewModel(private val postService: PostService, private val userService: UserService, private val flowCallback: FlowCallback) :
    ViewModel(),
    PostAdapter.ClickListener {

    private var disposable: Disposable? = null

    private var postGetLiveData: MutableLiveData<ResultCallBack<PostResponse>> = MutableLiveData()
    private var postGetMoreLiveData: MutableLiveData<SingleLiveEvent<ResultCallBack<PostResponse>>> = MutableLiveData()
    private var profileGetLiveData: MutableLiveData<ResultCallBack<UserResponse>> = MutableLiveData()

    private var postAdapter: PostAdapter = PostAdapter(this)
    private var isRefresh: ObservableBoolean = ObservableBoolean(false)
    private var isLoading: ObservableBoolean = ObservableBoolean(false)

    var profileModel: UserModel = UserModel()

    fun getPosts(): Pair<MutableLiveData<ResultCallBack<PostResponse>>, MutableLiveData<ResultCallBack<UserResponse>>> {
        disposable = Observables.zip(
            postService.all(pageNum = 0),
            userService.get()
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                onRefresh(true)
            }.doOnComplete {
                onLoadingHide()
            }.doFinally {
                onRefresh(false)
            }.doOnError {
                postGetLiveData.postValue(ResultCallBack.error(it.message.toString()))
                profileGetLiveData.postValue(ResultCallBack.error(it.message.toString()))
                onLoadingShow()
            }.subscribe(
                { result ->
                    val postResponse = result.first
                    val userResponse = result.second
                    postGetLiveData.postValue(ResultCallBack.success(postResponse))
                    profileGetLiveData.postValue(ResultCallBack.success(userResponse))
                },
                { e ->
                    postGetLiveData.postValue(ResultCallBack.error(e.message.toString()))
                    profileGetLiveData.postValue(ResultCallBack.error(e.message.toString()))
                }
            )

        return Pair(postGetLiveData, profileGetLiveData)
    }

    fun getPostMore(page:Int): MutableLiveData<SingleLiveEvent<ResultCallBack<PostResponse>>> {
        postService.all(page).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
            }.doOnComplete {
            }.doFinally {
            }.doOnError {
            }.subscribe(
                { result ->
                    postGetMoreLiveData.postValue(SingleLiveEvent(ResultCallBack.success(result)))
                },
                { e ->
                    postGetMoreLiveData.postValue(SingleLiveEvent(ResultCallBack.error(e.message.toString())))
                }
            )

        return postGetMoreLiveData
    }

    fun setAdapter(list: ArrayList<PostResponseDto>) {
        postAdapter.set(list)
    }

    fun loadAdapter(list: ArrayList<PostResponseDto>) {
        postAdapter.setAll(list)
    }

    fun getAdapter(): PostAdapter {
        return postAdapter
    }

    fun getIsRefresh(): ObservableBoolean {
        return isRefresh
    }

    fun onRefresh() {
        isRefresh.set(true)
        flowCallback.onRefresh()
    }

    fun onRefresh(status: Boolean) {
        isRefresh.set(status)
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

    override fun onClick(position: Int) {
        postAdapter.get(position)?.let { flowCallback.onPost(it) }
    }

    fun onPostPublishClick() {
        flowCallback.onPostPublish()
    }

    fun onProfileClick() {
        flowCallback.onProfile()
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}