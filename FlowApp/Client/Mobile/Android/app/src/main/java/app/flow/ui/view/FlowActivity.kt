package app.flow.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.flow.App.Companion.getApp
import app.flow.R
import app.flow.data.network.api.ApiSession
import app.flow.data.network.dto.response.PostResponseDto
import app.flow.data.network.service.PostService
import app.flow.data.network.service.UserService
import app.flow.databinding.ActivityFlowBinding
import app.flow.ui.helper.callback.FlowCallback
import app.flow.ui.helper.factory.FlowViewModelFactory
import app.flow.ui.view.profile.ProfileActivity
import app.flow.ui.viewmodel.FlowViewModel
import app.flow.util.helper.PaginationListener
import app.flow.util.result.Status
import javax.inject.Inject

class FlowActivity : AppCompatActivity(), FlowCallback {

    @Inject
    lateinit var postService: PostService

    @Inject
    lateinit var userService: UserService

    @Inject
    lateinit var apiSession: ApiSession

    private lateinit var binding: ActivityFlowBinding
    private lateinit var viewModel: FlowViewModel
    private var pageNumber = 0
    private var isMore = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getApp.component.inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_flow)
        viewModel = ViewModelProvider(this, FlowViewModelFactory(postService, userService, this)).get(FlowViewModel::class.java)
        binding.viewModel = viewModel

        onPostMore()
    }

    override fun onStart() {
        super.onStart()
        onPosts()
    }

    private fun onPostMore() {
        val linearLayoutManager = binding.recyclerViewFlow.layoutManager as LinearLayoutManager

        binding.recyclerViewFlow.addOnScrollListener(object : PaginationListener(linearLayoutManager) {
            override fun loadPostItems() {
                isMore = true
                pageNumber += 1

                viewModel.getPostMore(pageNumber).observe(this@FlowActivity, Observer {
                    it?.takeIf { isMore }?.getContentIfNotHandled()?.let { callback ->
                        when (callback.status) {
                            Status.SUCCESS -> {
                                callback.data.let { postResponse ->
                                    if (postResponse != null) {
                                        if (postResponse.data != null && postResponse.data.size > 0) {
                                            viewModel.loadAdapter(postResponse.data)
                                        }
                                    }
                                }

                                isMore = false
                            }
                            Status.ERROR -> {
                            }
                        }
                    }
                })
            }

            override fun isLoading(): Boolean {
                return isMore
            }
        })
    }

    private fun onPosts() {
        pageNumber = 0
        val result = viewModel.getPosts()

        result.first.observe(this, Observer { it ->
            when (it.status) {
                Status.SUCCESS -> {
                    it.data.let { postsResponse ->
                        if (postsResponse?.statusCode == 200) {
                            postsResponse.data?.let {
                                viewModel.setAdapter(it)
                                viewModel.onRefresh(false)
                            }
                        }
                    }
                }

                Status.ERROR -> {
                    apiSession.logout()
                    startActivity(Intent(applicationContext, SplashActivity::class.java))
                    finish()
                }
            }
        })

        result.second.observe(this, Observer { callback ->
            when (callback.status) {
                Status.SUCCESS -> {
                    val profileResponse = callback.data

                    if (profileResponse?.statusCode == 200) {
                        profileResponse.data?.let { userResponseDto ->
                            userResponseDto.img.let {
                                if (it != null) {
                                    viewModel.profileModel.img = it
                                }
                            }
                        }
                    }
                }

                Status.ERROR -> {
                }
            }
        })
    }

    override fun onPost(postResponseDto: PostResponseDto) {
        startActivity(Intent(applicationContext, PostActivity::class.java).putExtra("postId", postResponseDto.postId))
    }

    override fun onPostPublish() {
        startActivity(Intent(applicationContext, PublishActivity::class.java))
    }

    override fun onRefresh() {
        onPosts()
    }

    override fun onProfile() {
        startActivity(Intent(applicationContext, ProfileActivity::class.java))
    }
}