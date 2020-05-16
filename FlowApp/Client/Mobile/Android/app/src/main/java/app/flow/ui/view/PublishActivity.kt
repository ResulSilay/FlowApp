package app.flow.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.flow.App.Companion.getApp
import app.flow.R
import app.flow.data.network.api.ApiSession
import app.flow.data.network.dto.request.PostSaveRequestDto
import app.flow.data.network.service.PostService
import app.flow.databinding.ActivityPublishBinding
import app.flow.ui.helper.callback.PublishCallback
import app.flow.ui.helper.factory.PublishViewModelFactory
import app.flow.ui.viewmodel.PublishViewModel
import app.flow.util.ImageUtil
import app.flow.util.result.Status
import javax.inject.Inject

class PublishActivity : AppCompatActivity(), PublishCallback {

    @Inject
    lateinit var postService: PostService

    @Inject
    lateinit var apiSession: ApiSession

    private lateinit var binding: ActivityPublishBinding
    private lateinit var viewModel: PublishViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getApp.component.inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_publish)
        viewModel = ViewModelProvider(this, PublishViewModelFactory(postService, this)).get(PublishViewModel::class.java)
        binding.viewModel = viewModel
    }

    override fun onPublish() {
        if (viewModel.publishModel.content.length < 5) {
            Toast.makeText(applicationContext, getString(R.string.publish_page_valid_context_message), Toast.LENGTH_LONG).show()
            return
        }

        val postSaveRequestDto = PostSaveRequestDto().apply {
            content = viewModel.publishModel.content
            images = viewModel.publishModel.images
        }

        viewModel.publishPost(postSaveRequestDto).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    if (it.data?.statusCode == 201) {
                        Toast.makeText(applicationContext, getString(R.string.publish_page_success), Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Toast.makeText(applicationContext, getString(R.string.publish_page_failure), Toast.LENGTH_LONG).show()
                    }
                }
                Status.ERROR -> {
                }
            }
        })
    }

    override fun onImageAdd() {
        try {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE_CODE)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_IMAGE_CODE) {
            try {
                if (data == null)
                    return

                val uri = data.data

                if (uri != null) {
                    viewModel.publishModel.images.clear()
                    val bitmap = ImageUtil.getMediaBitmap(uri, applicationContext.contentResolver)
                    val base64String = ImageUtil.getBase64Encode(bitmap)
                    viewModel.publishModel.picture = bitmap
                    base64String?.let {
                        viewModel.publishModel.images.add(it)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        private const val SELECT_IMAGE_CODE = 1
    }
}