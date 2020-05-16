package app.flow

import android.app.Activity
import android.app.Application
import app.flow.di.component.AppComponent
import app.flow.di.component.DaggerAppComponent
import app.flow.di.module.AppModule
import app.flow.di.module.NetworkModule

class App : Application() {

    companion object {
        val Activity.getApp: App get() = application as App
    }

    val component: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }
}