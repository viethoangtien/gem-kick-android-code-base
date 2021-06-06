package com.gem.mediaplayers.di.component

import android.app.Application
import com.gem.mediaplayers.application.MainApp
import com.gem.mediaplayers.di.builder.ActivityBuilder
import com.gem.mediaplayers.di.builder.DialogFragmentBuilder
import com.gem.mediaplayers.di.builder.FragmentBuilder
import com.gem.mediaplayers.di.builder.ServiceBuilder
import com.gem.mediaplayers.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityBuilder::class,
        ServiceBuilder::class,
        FragmentBuilder::class,
        DialogFragmentBuilder::class
    ]
)
interface AppComponent {

    fun inject(app: MainApp)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}