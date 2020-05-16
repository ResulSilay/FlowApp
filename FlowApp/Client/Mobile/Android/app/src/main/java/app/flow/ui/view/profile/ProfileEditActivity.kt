package app.flow.ui.view.profile

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
import app.flow.data.network.dto.request.ProfilePasswordRequestDto
import app.flow.data.network.dto.request.ProfilePictureRequestDto
import app.flow.data.network.dto.request.ProfileRequestDto
import app.flow.data.network.service.UserService
import app.flow.databinding.ActivityProfileEditBinding
import app.flow.ui.helper.callback.ProfileEditCallback
import app.flow.ui.helper.factory.ProfileEditViewModelFactory
import app.flow.ui.viewmodel.ProfileEditViewModel
import app.flow.util.ImageUtil
import app.flow.util.result.Status
import javax.inject.Inject

class ProfileEditActivity : AppCompatActivity(), ProfileEditCallback {

    @Inject
    lateinit var userService: UserService

    @Inject
    lateinit var apiSession: ApiSession

    private lateinit var binding: ActivityProfileEditBinding
    private lateinit var viewModel: ProfileEditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getApp.component.inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_edit)
        viewModel = ViewModelProvider(this, ProfileEditViewModelFactory(userService, this)).get(ProfileEditViewModel::class.java)
        binding.viewModel = viewModel
    }

    override fun onStart() {
        super.onStart()
        onProfile()
    }

    private fun onProfile() {
        viewModel.get().observe(this, Observer { callback ->
            when (callback.status) {
                Status.SUCCESS -> {
                    val result = callback.data
                    if (result?.statusCode == 200) {
                        result.data?.let {
                            viewModel.profileModel.apply {
                                name = it.username.toString()
                                email = it.email.toString()
                                phone = it.phone.toString()
                                address = it.address.toString()
                                img = it.img.let { it }
                            }
                        }
                    }
                }
                Status.ERROR -> {

                }
            }
        })
    }

    override fun onChangeInfo() {
        if (viewModel.profileModel.name.isEmpty() ||
            viewModel.profileModel.phone.isEmpty() ||
            viewModel.profileModel.address.isEmpty()
        ) {
            Toast.makeText(applicationContext, getString(R.string.auth_register_valid_info_message), Toast.LENGTH_SHORT).show()
            return
        }

        val profileRequestDto = ProfileRequestDto().apply {
            name = viewModel.profileModel.name
            phone = viewModel.profileModel.phone
            address = viewModel.profileModel.address
        }

        viewModel.save(profileRequestDto).observe(this, Observer { callback ->
            when (callback.status) {
                Status.SUCCESS -> {
                    val result = callback.data
                    if (result?.statusCode == 200) {
                        Toast.makeText(applicationContext, getString(R.string.profile_edit_page_saved), Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
                Status.ERROR -> {

                }
            }
        })
    }

    override fun onChangePassword() {
        try {
            if (viewModel.profileModel.password.length < 6) {
                Toast.makeText(applicationContext, getString(R.string.auth_register_valid_password_message), Toast.LENGTH_SHORT).show()
                return
            }

            val profilePasswordRequestDto = ProfilePasswordRequestDto().apply {
                password = viewModel.profileModel.password
            }

            viewModel.changePassword(profilePasswordRequestDto).observe(this, Observer { callback ->
                when (callback.status) {
                    Status.SUCCESS -> {
                        val result = callback.data
                        if (result?.statusCode == 200) {
                            Toast.makeText(applicationContext, "Changed password.", Toast.LENGTH_LONG).show()
                            finish()
                        } else {
                            Toast.makeText(applicationContext, "Error not 200 code", Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {

                    }
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onChangeImage() {
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

            if (data == null)
                return

            val uri = data.data

            if (uri != null) {
                val bitmap = ImageUtil.getMediaBitmap(uri, applicationContext.contentResolver)
                val base64String = ImageUtil.getBase64Encode(bitmap)

                val profilePictureRequestDto = ProfilePictureRequestDto().apply {
                    img = base64String
                }

                viewModel.changPicture(profilePictureRequestDto).observe(this, Observer { callback ->
                    when (callback.status) {
                        Status.SUCCESS -> {
                            val result = callback.data
                            if (result?.statusCode == 200) {
                                Toast.makeText(applicationContext, "Changed Picture.", Toast.LENGTH_LONG).show()
                                finish()
                            } else {
                                Toast.makeText(applicationContext, "Error not 200 code", Toast.LENGTH_LONG).show()
                            }
                        }
                        Status.ERROR -> {

                        }
                    }
                })
            }
        }
    }

    companion object {
        private const val SELECT_IMAGE_CODE = 1
    }
}