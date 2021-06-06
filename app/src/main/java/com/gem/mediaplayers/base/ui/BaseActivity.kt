package com.gem.mediaplayers.base.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.gem.mediaplayers.BR
import com.gem.mediaplayers.R
import com.gem.mediaplayers.base.dialog.BaseConfirmDialog
import com.gem.mediaplayers.base.dialog.BaseLoadingDialog
import com.gem.mediaplayers.base.viewmodel.BaseViewModel
import com.gem.mediaplayers.data.model.Confirm
import com.gem.mediaplayers.data.network.exception.KickException
import com.gem.mediaplayers.data.network.response.ErrorResponse
import com.gem.mediaplayers.di.viewmodel.ViewModelProviderFactory
import com.gem.mediaplayers.utils.*
import com.gem.mediaplayers.utils.AppConstants.ArgumentDialogFragment.CONFIRM
import com.gem.mediaplayers.utils.extension.hasNetworkConnection
import com.gem.mediaplayers.utils.extension.showDialogFragment
import com.google.gson.Gson
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel>() :
    AppCompatActivity(), BaseFragment.Callback, ServerErrorListener {

    @Inject
    lateinit var factory: ViewModelProviderFactory

    lateinit var binding: T
    private var mViewModel: V? = null
    private var cancelOnTouch = true
    private var isShowingSystemError = false
    private lateinit var loadingDialog: BaseLoadingDialog
    protected var compositeDisposable = CompositeDisposable()
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

    override fun onFragmentAttached() {}

    override fun onFragmentDetached(tag: String?) {}

    open fun getViewDataBinding(): T {
        return binding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        performDataBinding()
        initProgressDialog()
        initView()
        subscribeLiveData()
        loadData()
        initListener()

        mViewModel?.isLoadingLiveData?.observe(this, Observer { isLoading ->
            isLoading?.let {
                if (isLoading) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }
        })
        mViewModel?.serverErrorListener = this
    }

    private fun initProgressDialog() {
        loadingDialog = BaseLoadingDialog.getInstance(this)
    }

    override fun onResume() {
        super.onResume()
        clearChildFocus()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear() //continue add dispose
    }

    override fun onDestroy() {
        super.onDestroy()
        rxCompositeDisposable.clear()
    }

    private fun clearChildFocus() {
        val rootView = window.decorView.findViewById<View>(android.R.id.content)
        rootView.apply {
            isFocusable = true
            isFocusableInTouchMode = true
            requestFocus()
        }
    }

    open fun fetchCurrentLanguage() {
        //TODO
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        //Hide soft keyboard when user touch screen
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText || v is SearchView) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    override fun handleError(title: String?, throwable: Throwable, isFinish: Boolean?) {
        hideLoading()
        showError(title, throwable, isFinish!!)
        handleError()
    }

    override fun handleShowMessage(message: String, isFinish: Boolean?) {
        hideLoading()
        showErrorMessage(message)
    }

    open fun initView() = Unit
    open fun subscribeLiveData() = Unit
    open fun initListener() = Unit
    open fun loadData() = Unit

    protected open fun handleError() = Unit

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
        view?.clearFocus()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun hideKeyboardWhenClickOutsideEdittext(view: View) {
        if (view !is EditText) {
            view.setOnTouchListener { _, _ ->
                hideKeyboard()
                false
            }
        }

        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                hideKeyboardWhenClickOutsideEdittext(innerView)
            }
        }
    }

    fun hideLoading() {
        loadingDialog.hideLoadingDialog()
    }

    fun showLoading() {
        loadingDialog.showLoadingDialog()
    }

    private fun showError(title: String?, throwable: Throwable, isFinish: Boolean = false) {
        when (throwable) {
            is ConnectException, is UnknownHostException -> {
                if (!isShowingSystemError) {
                    showErrorMessage(R.string.no_internet_connection_error)
                    isShowingSystemError = true
                }
            }
            is HttpException -> {
                if (!isShowingSystemError) {
                    val message = try {
                        val content = throwable.response()!!.errorBody()!!.string()
                        val responseHttp = Gson().fromJson(
                            content,
                            ErrorResponse::class.java
                        )
                        responseHttp.message
                    } catch (e: Exception) {
                        throwable.message()
                    }
                    handleNetworkException(
                        title = null,
                        message = message,
                        code = throwable.code(),
                        isFinish = isFinish
                    )
                    isShowingSystemError = true
                }
            }
            is KickException -> {
                handleNetworkException(
                    title = title,
                    message = throwable.message,
                    code = throwable.statusCode,
                    isFinish = isFinish
                )
            }
            else -> {
                handleNetworkException(null, message = throwable.message)
            }
        }
    }

    private fun handleNetworkException(
        title: String?,
        message: String?,
        code: Int = 0,
        isFinish: Boolean = false
    ) {
        if (message == null) return
        showMessage(title = title,
            onPositiveListener = {
                when {
                    isFinish -> finish()
                    code == 401 -> logoutWhenAccountRevoked()
                }
                isShowingSystemError = false
            }
        )
    }

    private fun logoutWhenAccountRevoked() {
        //TODO
    }

    fun showErrorMessage(@StringRes resId: Int) {
        showErrorMessage(message = getString(resId))
    }

    fun showErrorMessage(message: String) {
        showMessage(
            title = message,
            positiveTitleRes = R.string.ok
        )
    }

    fun showConfirmMessage(
        @StringRes messageResId: Int,
        @StringRes positiveTitleRes: Int = R.string.ok,
        @StringRes negativeTitleRes: Int = R.string.cancel,
        onPositiveListener: (() -> Unit)? = null
    ) {
        showMessage(
            title = getString(messageResId),
            positiveTitleRes = positiveTitleRes,
            negativeTitleRes = negativeTitleRes,
            onPositiveListener = onPositiveListener
        )
    }

    fun showMessage(
        title: String? = null,
        @StringRes positiveTitleRes: Int = R.string.ok,
        @StringRes negativeTitleRes: Int? = null,
        onPositiveListener: (() -> Unit)? = null,
        onNegativeListener: (() -> Unit)? = null
    ) {
        showDialogFragment(
            type = BaseConfirmDialog::class.java,
            data = hashMapOf(
                CONFIRM to Confirm(
                    title,
                    positiveTitleRes,
                    negativeTitleRes
                )
            ),
            func = {
                setOnPositiveListener {
                    onPositiveListener?.invoke()
                }
                setOnNegativeListener {
                    onNegativeListener?.invoke()
                }
            }
        )
    }

    val isNetworkConnected: Boolean
        get() = hasNetworkConnection()

    private fun performDependencyInjection() {
        AndroidInjection.inject(this)
    }

    private fun performDataBinding() {
        mViewModel = if (mViewModel == null) getViewModel() else mViewModel
        //Set language before set content view
        fetchCurrentLanguage()
        binding = DataBindingUtil.setContentView(this@BaseActivity, layoutId)
        binding.executePendingBindings()
    }

    fun addFragment(viewId: Int, fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.add(viewId, fragment).commit()
    }

    fun replaceFragment(viewId: Int, fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(viewId, fragment).commit()
    }

    protected fun showPopupConfirmFinish(isQuit: Boolean = true) {
        showMessage(
            title = getString(if (isQuit) R.string.are_you_sure_quit else R.string.are_you_sure_cancel),
            positiveTitleRes = R.string.yes,
            negativeTitleRes = R.string.no,
            onNegativeListener = {
                //do-something
            },
            onPositiveListener = {
                finish()
            }
        )
    }
}