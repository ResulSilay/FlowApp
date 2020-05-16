package app.flow.ui.view.auth

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.flow.App.Companion.getApp
import app.flow.R
import app.flow.data.network.dto.request.RegisterRequestDto
import app.flow.data.network.service.AuthService
import app.flow.databinding.ActivityAuthRegisterBinding
import app.flow.ui.helper.callback.auth.RegisterCallback
import app.flow.ui.helper.factory.auth.RegisterViewModelFactory
import app.flow.ui.viewmodel.auth.RegisterViewModel
import app.flow.util.GeneralUtil
import app.flow.util.result.Status
import javax.inject.Inject

class RegisterActivity : AppCompatActivity(), RegisterCallback {

    @Inject
    lateinit var authService: AuthService

    private lateinit var binding: ActivityAuthRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        getApp.component.inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth_register)
        viewModel = ViewModelProvider(this, RegisterViewModelFactory(authService, this)).get(RegisterViewModel::class.java)
        binding.viewModel = viewModel
    }

    override fun onRegister() {

        if(!GeneralUtil.isValidEmail(viewModel.registerModel.email))
        {
            Toast.makeText(applicationContext, getString(R.string.auth_register_valid_email_message), Toast.LENGTH_SHORT).show()
            return
        }

        if (
            viewModel.registerModel.name.isEmpty() ||
            viewModel.registerModel.phone.isEmpty() ||
            viewModel.registerModel.address.isEmpty()
        ) {
            Toast.makeText(applicationContext, getString(R.string.auth_register_valid_info_message), Toast.LENGTH_SHORT).show()
            return
        }

        if (viewModel.registerModel.password.length < 6) {
            Toast.makeText(applicationContext, getString(R.string.auth_register_valid_password_message), Toast.LENGTH_SHORT).show()
            return
        }

        val registerRequestDto = RegisterRequestDto().apply {
            this.username = viewModel.registerModel.name
            this.email = viewModel.registerModel.email
            this.password = viewModel.registerModel.password
            this.phone = viewModel.registerModel.phone
            this.address = viewModel.registerModel.address
        }

        viewModel.register(registerRequestDto).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    val result = it.data
                    if (result?.statusCode == 201) {
                        finish()
                        Toast.makeText(applicationContext, getString(R.string.auth_register_success_message), Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(applicationContext, getString(R.string.auth_register_error_message), Toast.LENGTH_LONG).show()
                    }
                }
                Status.ERROR -> {
                }
            }
        })
    }
}