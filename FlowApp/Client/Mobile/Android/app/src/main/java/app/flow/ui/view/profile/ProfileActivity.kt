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
import app.flow.data.network.service.UserService
import app.flow.databinding.ActivityProfileBinding
import app.flow.ui.helper.callback.ProfileCallback
import app.flow.ui.helper.factory.ProfileViewModelFactory
import app.flow.ui.view.SplashActivity
import app.flow.ui.viewmodel.ProfileViewModel
import app.flow.util.result.Status
import javax.inject.Inject

class ProfileActivity : AppCompatActivity(), ProfileCallback {

    @Inject
    lateinit var userService: UserService

    @Inject
    lateinit var apiSession: ApiSession

    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getApp.component.inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        viewModel = ViewModelProvider(this, ProfileViewModelFactory(userService, this)).get(ProfileViewModel::class.java)
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
                    val data = callback.data
                    if (data?.statusCode == 200) {
                        data.data?.let {
                            viewModel.profileModel.apply {
                                name = it.username.toString()
                                email = it.email.toString()
                                phone = it.phone.toString()
                                address = it.address.toString()
                                img = it.img.let { it }
                            }
                        }
                    } else {
                        Toast.makeText(applicationContext, getString(R.string.TODO), Toast.LENGTH_LONG).show()
                    }
                }
                Status.ERROR -> {
                }
            }
        })
    }

    override fun onLogout() {
        apiSession.logout()
        startActivity(Intent(applicationContext, SplashActivity::class.java))
        finish()
    }

    override fun onEdit() {
        startActivity(Intent(applicationContext, ProfileEditActivity::class.java))
    }
}