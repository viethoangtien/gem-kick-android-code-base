package com.gem.mediaplayers.base.ui

import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.gem.mediaplayers.BR
import com.gem.mediaplayers.R
import com.gem.mediaplayers.base.viewmodel.BaseViewModel
import com.gem.mediaplayers.di.viewmodel.ViewModelProviderFactory
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> : DaggerFragment(),
    ServerErrorListener {

    @Inject
    lateinit var factory: ViewModelProviderFactory

    var baseActivity: BaseActivity<*, *>? = null
        private set
    private var mRootView: View? = null
    lateinit var binding: T
        private set
    private var mViewModel: V? = null
    protected var rxCompositeDisposable = CompositeDisposable()

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */

    abstract fun getViewModel(): V?

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            baseActivity = context
            context.onFragmentAttached()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        mViewModel = if (mViewModel == null) getViewModel() else mViewModel
    }

    open fun initView() = Unit
    open fun initListener() = Unit
    open fun subscribeLiveData() = Unit
    open fun loadData() = Unit

    protected open fun handleError() = Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val contextThemeWrapper = ContextThemeWrapper(
            activity,
            R.style.AppTheme
        )

        val localInflater = inflater.cloneInContext(contextThemeWrapper)
        binding = DataBindingUtil.inflate(localInflater, layoutId, container, false)
        mRootView = binding.root
        return mRootView
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        initView()
        subscribeLiveData()
        initListener()

        baseActivity?.let {
            mViewModel?.isLoadingLiveData?.observe(it, Observer {
                if (it) {
                    showLoading()
                } else {
                    hideLoading()
                }
            })
        }
        mViewModel?.serverErrorListener = this
    }

    override fun handleError(title: String?, throwable: Throwable, isFinish: Boolean?) {
        baseActivity?.handleError(title, throwable)
        handleError()
    }

    override fun handleShowMessage(message: String, isFinish: Boolean?) {
        baseActivity?.handleShowMessage(message, isFinish)
    }

    fun hideKeyboard() {
        baseActivity?.hideKeyboard()
    }

    fun showLoading() {
        baseActivity?.showLoading()
    }

    fun hideLoading() {
        baseActivity?.hideLoading()
    }

    val isNetworkConnected: Boolean
        get() = baseActivity != null && baseActivity!!.isNetworkConnected

    private fun performDependencyInjection() {
        AndroidSupportInjection.inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        rxCompositeDisposable.clear()
    }

    interface Callback {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String?)
    }
}