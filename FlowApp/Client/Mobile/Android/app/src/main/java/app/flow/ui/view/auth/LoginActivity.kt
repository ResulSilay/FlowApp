package app.flow.ui.view.auth

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.flow.App.Companion.getApp
import app.flow.R
import app.flow.data.network.api.ApiSession
import app.flow.data.network.dto.request.LoginRequestDto
import app.flow.data.network.service.AuthService
import app.flow.databinding.ActivityAuthLoginBinding
import app.flow.ui.helper.callback.auth.LoginCallback
import app.flow.ui.helper.factory.auth.LoginViewModelFactory
import app.flow.ui.view.FlowActivity
import app.flow.ui.viewmodel.auth.LoginViewModel
import app.flow.util.result.Status
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), LoginCallback {

    @Inject
    lateinit var authService: AuthService

    private lateinit var binding: ActivityAuthLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        getApp.component.inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth_login)
        viewModel = ViewModelProvider(this, LoginViewModelFactory(authService, this)).get(LoginViewModel::class.java)
        binding.viewModel = viewModel
    }

    override fun onLogin() {
        if (!viewModel.user.email.contains("@")) {
            Toast.makeText(applicationContext, getString(R.string.auth_login_valid_email_message), Toast.LENGTH_SHORT).show()
            return
        }

        if (viewModel.user.password.isEmpty()) {
            Toast.makeText(applicationContext, getString(R.string.auth_login_valid_password_message), Toast.LENGTH_SHORT).show()
            return
        }

        val loginRequestDto = LoginRequestDto().apply {
            this.email = viewModel.user.email
            this.password = viewModel.user.password
        }

        viewModel.login(loginRequestDto).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data.let { result ->
                        if (result != null) {
                            if (result.statusCode == 200 && result.success!!) {
                                result.data.let { token ->
                                    if (token != null) {
                                        ApiSession.getInstance(applicationContext).setToken(token.toString())
                                        startActivity(Intent(applicationContext, FlowActivity::class.java))
                                        finish()
                                    }
                                }
                            } else {
                                Toast.makeText(applicationContext, getString(R.string.auth_login_invalid_message), Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }

                Status.ERROR -> {
                    //Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onRegister() {
        startActivity(Intent(applicationContext, RegisterActivity::class.java))
    }
}