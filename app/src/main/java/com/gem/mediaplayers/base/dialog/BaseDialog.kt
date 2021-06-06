package com.gem.mediaplayers.base.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.gem.mediaplayers.BR
import com.gem.mediaplayers.R
import com.gem.mediaplayers.base.ui.BaseActivity
import com.gem.mediaplayers.base.ui.ServerErrorListener
import com.gem.mediaplayers.base.viewmodel.BaseViewModel
import com.gem.mediaplayers.di.viewmodel.ViewModelProviderFactory
import com.gem.mediaplayers.utils.extension.put
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

abstract class BaseDialog<T : ViewDataBinding, V : BaseViewModel>(
    val cancelable: Boolean = false,
    val isAnimation: Boolean = false
) :
    DialogFragment(),
    ServerErrorListener {

    var baseActivity: BaseActivity<*, *>? = null

    private var mRootView: View? = null
    var binding: T? = null
        private set
    private var mViewModel: V? = null

    protected val compositeDisposable = CompositeDisposable()

    @get:LayoutRes
    abstract val layoutId: Int

    @Inject
    lateinit var factory: ViewModelProviderFactory

    abstract fun getViewModel(): V?

    override fun onAttach(context: Context) {
        super.onAttach(context)
        performDependencyInjection()
        if (context is BaseActivity<*, *>) {
            baseActivity = context
            context.onFragmentAttached()
        }
    }

    private fun performDependencyInjection() {
        AndroidSupportInjection.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = if (mViewModel == null) getViewModel() else mViewModel
        setHasOptionsMenu(false)
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        mRootView = binding!!.root
        mViewModel?.serverErrorListener = this
        dialog?.window?.apply {
            requestFeature(Window.FEATURE_NO_TITLE)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        if(isAnimation){
            dialog?.window?.attributes?.windowAnimations = R.style.DialogFragmentAnimation;
        }
        isCancelable = cancelable
        dialog?.setCanceledOnTouchOutside(cancelable)
        return mRootView
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    override fun onStart() {
        super.onStart()
        setSizeForDialogFragment()
    }

    private fun setSizeForDialogFragment() {
        val size = Point()
        dialog?.window?.windowManager?.defaultDisplay?.getSize(size)
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }


    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.lifecycleOwner = this
        binding!!.executePendingBindings()
        initView()
        subscribeLiveData()
        initData()
        initListener()
    }

    open fun initView() = Unit
    open fun subscribeLiveData() = Unit
    open fun initListener() = Unit
    open fun initData() = Unit

    open fun setData(data: HashMap<String, Any>) {
        if (data.isEmpty()) {
            arguments = Bundle()
            return
        }
        val bundle = Bundle()
        for ((key, value) in data) {
            bundle.put(key, value)
        }
        arguments = bundle
    }

    override fun handleError(title: String?, throwable: Throwable, isFinish: Boolean?) {
        baseActivity?.handleError(title, throwable)
        handleError()
    }

    fun hideKeyboard() {
        val imm =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    fun showLoading() {
        baseActivity?.showLoading()
    }

    fun hideLoading() {
        baseActivity?.hideLoading()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    protected open fun handleError() = Unit
}