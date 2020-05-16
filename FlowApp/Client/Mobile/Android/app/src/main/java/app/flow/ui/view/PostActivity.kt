package app.flow.ui.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.flow.App.Companion.getApp
import app.flow.R
import app.flow.data.network.api.ApiSession
import app.flow.data.network.dto.request.RateRequestDto
import app.flow.data.network.service.PostService
import app.flow.data.network.service.RateService
import app.flow.databinding.ActivityPostBinding
import app.flow.ui.helper.callback.PostCallback
import app.flow.ui.helper.factory.PostViewModelFactory
import app.flow.ui.viewmodel.PostViewModel
import app.flow.util.DateUtil
import app.flow.util.result.Status
import javax.inject.Inject

class PostActivity : AppCompatActivity(), PostCallback {

    @Inject
    lateinit var postService: PostService

    @Inject
    lateinit var rateService: RateService

    @Inject
    lateinit var apiSession: ApiSession

    private lateinit var binding: ActivityPostBinding
    private lateinit var viewModel: PostViewModel

    private var _postId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getApp.component.inject(this)

        val extras = intent.extras
        if (extras != null) _postId = extras.getInt("postId") else {
            finish()
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_post)
        viewModel = ViewModelProvider(this, PostViewModelFactory(postService, rateService, this)).get(PostViewModel::class.java)
        binding.viewModel = viewModel
    }

    override fun onStart() {
        super.onStart()
        onPost()
    }

    private fun onPost() {
        val result = viewModel.getPost(_postId)

        result.first.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    val postResponse = it.data
                    if (postResponse?.statusCode == 200) {
                        postResponse.data.let { post ->
                            viewModel.postModel.apply {
                                if (post != null) {
                                    post.img?.let { it ->
                                        img = it
                                    }

                                    name = post.username
                                    content = post.content.toString()
                                    rate = post.rateValue
                                    picture = post.picture.toString()

                                    post.createdDateTime.let {
                                        createdDateTime = DateUtil.getDateFormat(it)
                                    }
                                }
                            }
                        }
                    } else {
                        Toast.makeText(applicationContext, getString(R.string.auth_register_error_message), Toast.LENGTH_LONG).show()
                    }
                }

                Status.ERROR -> {
                }
            }
        })

        result.second.observe(this, Observer { callback ->
            when (callback.status) {
                Status.SUCCESS -> {
                    val rateResponse = callback.data
                    if (rateResponse?.isSuccessful!!) {
                        rateResponse.body()?.data.let { it ->
                            viewModel.rateModel.apply {
                                it?.rate?.let {
                                    rate = it
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

    override fun onRate() {
        val rateRequestDto = RateRequestDto().apply {
            this.postId = _postId
            this.rate = viewModel.rateModel.rate
        }

        viewModel.rate(rateRequestDto).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    if (it.data?.isSuccessful!!) {
                        Toast.makeText(applicationContext, getString(R.string.post_page_rating_success_message), Toast.LENGTH_LONG).show()
                        onPost()
                    } else {
                        Toast.makeText(applicationContext, getString(R.string.post_page_rating_failure_message), Toast.LENGTH_LONG).show()
                    }
                }

                Status.ERROR -> {
                }
            }
        })
    }
}