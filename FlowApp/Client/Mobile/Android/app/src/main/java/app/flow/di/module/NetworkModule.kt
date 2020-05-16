package app.flow.di.module

import android.app.Application
import app.flow.data.network.api.ApiClient
import app.flow.data.network.api.ApiSession
import app.flow.data.network.service.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule(var application: Application) {

    @Singleton
    @Provides
    fun provideApiClient(): ApiClient {
        return ApiClient.getInstance(application.applicationContext)
    }

    @Singleton
    @Provides
    fun provideApiSession(): ApiSession {
        return ApiSession.getInstance(application.applicationContext)
    }

    @Singleton
    @Provides
    fun provideAuthService(): AuthService {
        return AuthService(application.applicationContext)
    }

    @Singleton
    @Provides
    fun providePostService(): PostService {
        return PostService(application.applicationContext)
    }

    @Singleton
    @Provides
    fun provideUserService(): UserService {
        return UserService(application.applicationContext)
    }

    @Singleton
    @Provides
    fun provideRateService(): RateService {
        return RateService(application.applicationContext)
    }
}