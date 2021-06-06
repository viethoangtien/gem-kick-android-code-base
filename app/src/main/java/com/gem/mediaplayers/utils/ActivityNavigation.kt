package com.gem.mediaplayers.utils

import android.app.Activity
import com.gem.mediaplayers.ui.splash.SplashActivity
import com.gem.mediaplayers.utils.extension.launchActivity

object ActivityNavigation {

    const val EXTRAS_DATA = "EXTRAS_DATA"
    const val EXTRAS_DATA1 = "EXTRAS_DATA1"
    const val EXTRAS_DATA2 = "EXTRAS_DATA2"
    const val EXTRAS_DATA3 = "EXTRAS_DATA3"
    const val EXTRAS_DATA4 = "EXTRAS_DATA4"
    const val EXTRAS_DATA5 = "EXTRAS_DATA5"
    const val EXTRAS_DATA6 = "EXTRAS_DATA6"
    const val EXTRAS_DATA7 = "EXTRAS_DATA7"

    fun startSplashActivity(from: Activity, isFinish: Boolean = false) {
        from.launchActivity<SplashActivity>()
    }
}
