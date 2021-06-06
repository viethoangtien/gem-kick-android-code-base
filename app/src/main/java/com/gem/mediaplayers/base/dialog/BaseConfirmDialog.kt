package com.gem.mediaplayers.base.dialog

import com.gem.mediaplayers.R
import com.gem.mediaplayers.base.viewmodel.BaseViewModel
import com.gem.mediaplayers.data.model.Confirm
import com.gem.mediaplayers.databinding.DialogConfirmBinding
import com.gem.mediaplayers.utils.extension.argument
import com.gem.mediaplayers.utils.extension.gone
import com.gem.mediaplayers.utils.extension.invisible
import com.gem.mediaplayers.utils.extension.visible
import kotlinx.android.synthetic.main.dialog_confirm.*

class BaseConfirmDialog :
    BaseDialog<DialogConfirmBinding, BaseViewModel>(cancelable = true) {
    private val confirm: Confirm by argument()
    private var onPositiveListener: (() -> Unit)? = null
    private var onNegativeListener: (() -> Unit)? = null

    override val layoutId: Int
        get() = R.layout.dialog_confirm

    override fun getViewModel(): BaseViewModel? {
        return null
    }

    override fun handleShowMessage(message: String, isFinish: Boolean?) {

    }

    override fun initView() {
        super.initView()
        tv_title.text = confirm.title
        btn_ok.text = getString(confirm.positiveTitleRes)
        confirm.negativeTitleRes?.let {
            btn_cancel.visible()
            vertical_divider.visible()
            btn_cancel.text = getString(it)
        } ?: kotlin.run {
            btn_cancel.gone()
            vertical_divider.invisible()
        }
    }

    override fun initListener() {
        super.initListener()
        btn_ok.setOnClickListener {
            dismiss()
            onPositiveListener?.invoke()
        }
        btn_cancel.setOnClickListener {
            dismiss()
            onNegativeListener?.invoke()
        }
    }

    fun setOnPositiveListener(func: () -> Unit) {
        onPositiveListener = func
    }

    fun setOnNegativeListener(func: () -> Unit) {
        onNegativeListener = func
    }
}