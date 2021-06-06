package com.gem.mediaplayers.di.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.gem.mediaplayers.data.DataManager
import com.gem.mediaplayers.ui.splash.SplashViewModel
import com.gem.mediaplayers.utils.rx.SchedulerProvider
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
@Singleton
class ViewModelProviderFactory @Inject constructor(
    private val context: Context,
    private val dataManager: DataManager,
    private val schedulerProvider: SchedulerProvider
) :
    NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                return SplashViewModel(dataManager, schedulerProvider) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}