package com.gem.mediaplayers.ui.splash

import androidx.lifecycle.ViewModelProvider
import com.gem.mediaplayers.R
import com.gem.mediaplayers.base.ui.BaseActivity
import com.gem.mediaplayers.databinding.ActivitySplashBinding
import com.gem.mediaplayers.utils.extension.completableTimer
import com.gem.mediaplayers.utils.extension.loadAnimation
import com.gem.mediaplayers.utils.extension.visible

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {
    private lateinit var viewModel: SplashViewModel

    override val layoutId = R.layout.activity_splash

    override fun getViewModel(): SplashViewModel {
        viewModel = ViewModelProvider(this, factory)[SplashViewModel::class.java]
        return viewModel
    }

    override fun initView() {
        showLogo()
    }

    override fun loadData() {
    }

    override fun subscribeLiveData() {
    }

    private fun goToNextScreen() {
        completableTimer({
        })
    }

    private fun showLogo() {
        binding.imvLogoSplash.apply {
            visible()
            loadAnimation(this@SplashActivity, R.anim.fade_in)
        }
    }

}
