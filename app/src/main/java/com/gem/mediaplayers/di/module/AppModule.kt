package com.gem.mediaplayers.di.module

import android.app.Application
import android.content.Context
import com.gem.mediaplayers.data.network.header.AuthHeader
import com.gem.mediaplayers.data.AppDataManager
import com.gem.mediaplayers.data.DataManager
import com.gem.mediaplayers.data.local.AppPreferencesHelper
import com.gem.mediaplayers.data.local.PreferencesHelper
import com.gem.mediaplayers.data.network.ApiHelper
import com.gem.mediaplayers.data.network.AppApiHelper
import com.gem.mediaplayers.di.PreferenceInfo
import com.gem.mediaplayers.utils.AppConstants
import com.gem.mediaplayers.utils.rx.AppSchedulerProvider
import com.gem.mediaplayers.utils.rx.SchedulerProvider
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApiHelper(appApiHelper: AppApiHelper): ApiHelper {
        return appApiHelper
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideDataManager(appDataManager: AppDataManager): DataManager {
        return appDataManager
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @PreferenceInfo
    fun providePreferenceName(): String {
        return AppConstants.PREF_FILENAME
    }

    @Provides
    @Singleton
    fun providePreferencesHelper(appPreferencesHelper: AppPreferencesHelper): PreferencesHelper {
        return appPreferencesHelper
    }

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }

    @Provides
    @Singleton
    fun provideProtectHeader(preferencesHelper: PreferencesHelper): AuthHeader {
        val user = preferencesHelper.getCurrentUser()
        return AuthHeader(
            user?.userId,
            user?.token
        )
    }
}