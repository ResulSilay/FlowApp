package app.flow.di.component

import android.app.Application
import app.flow.ui.view.auth.LoginActivity
import app.flow.di.module.AppModule
import app.flow.di.module.NetworkModule
import app.flow.ui.view.*
import app.flow.ui.view.auth.RegisterActivity
import app.flow.ui.view.profile.ProfileActivity
import app.flow.ui.view.profile.ProfileEditActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [],
    modules = [AppModule::class, NetworkModule::class]
)
interface AppComponent {

    fun inject(application: Application)
    fun inject(splashActivity: SplashActivity)
    fun inject(loginActivity: LoginActivity)
    fun inject(registerActivity: RegisterActivity)

    fun inject(flowActivity: FlowActivity)
    fun inject(postActivity: PostActivity)
    fun inject(publishActivity: PublishActivity)
    fun inject(profileActivity: ProfileActivity)
    fun inject(profileEditActivity: ProfileEditActivity)

    fun application(): Application
}