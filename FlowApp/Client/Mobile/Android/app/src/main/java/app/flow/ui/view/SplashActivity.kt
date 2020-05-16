package app.flow.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.flow.App.Companion.getApp
import app.flow.R
import app.flow.data.network.api.ApiSession
import app.flow.ui.view.auth.LoginActivity
import app.flow.ui.view.auth.RegisterActivity
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var apiSession: ApiSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getApp.component.inject(this)
        setContentView(R.layout.activity_splash)

        if (apiSession.isAccount()) {
            startActivity(Intent(applicationContext, FlowActivity::class.java))
        } else {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }

        finish()
    }
}