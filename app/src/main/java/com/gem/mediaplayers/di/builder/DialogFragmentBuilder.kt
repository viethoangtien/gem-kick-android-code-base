package com.gem.mediaplayers.di.builder

import com.gem.mediaplayers.base.dialog.BaseConfirmDialog
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DialogFragmentBuilder {

    @ContributesAndroidInjector
    abstract fun bindBaseConfirmDialog(): BaseConfirmDialog

}
