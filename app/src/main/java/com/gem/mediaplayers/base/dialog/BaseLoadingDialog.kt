package com.gem.mediaplayers.base.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import com.gem.mediaplayers.R
import com.gem.mediaplayers.utils.extension.inflate
import com.gem.mediaplayers.utils.extension.loadImageAsGif
import kotlinx.android.synthetic.main.layout_loading.view.*

class BaseLoadingDialog private constructor(private var context: Context) {

    private var mDialog = Dialog(context)
    private var shown = false

    //Before initialize instance of class
    //Before class constructor
    companion object {
        fun getInstance(context: Context) = BaseLoadingDialog(context)
    }

    //After primary constructor
    init {
        val view = context.inflate(R.layout.layout_loading)
        mDialog.apply {
            setContentView(view)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            window?.apply {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
            val size = Point()
            window?.windowManager?.defaultDisplay?.getSize(size)
            window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
        }
        view.imv_loading.loadImageAsGif(context, R.drawable.cupertino_indicator)
    }

    fun showLoadingDialog() {
        if (!shown && !(context as Activity).isFinishing) {
            force()
            mDialog.show()
        }
    }

    fun hideLoadingDialog() {
        if (shown && (mDialog.isShowing)) {
            initialization()
            mDialog.dismiss()
        }
    }

    private fun force() {
        shown = true
    }

    private fun initialization() {
        shown = false
    }
}