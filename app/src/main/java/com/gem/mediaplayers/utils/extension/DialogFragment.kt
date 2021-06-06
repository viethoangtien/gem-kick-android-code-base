package com.gem.mediaplayers.utils.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.gem.mediaplayers.base.dialog.BaseDialog
import dagger.android.support.DaggerFragment
import java.util.*

fun <T : BaseDialog<*, *>> AppCompatActivity.showDialogFragment(
    type: Class<T>,
    data: Map<String, Any>? = null,
    func: (T.() -> Unit)? = null
): T {
    val dialog = type.newInstance().apply { func?.let { func() } }
    data?.let {
        dialog.setData(it as HashMap<String, Any>)
    }
    hideFragmentByTag(type.simpleName)
    dialog.show(supportFragmentManager, type.simpleName)
    return dialog
}

fun <T : BaseDialog<*, *>> DaggerFragment.showDialogFragment(
    type: Class<T>,
    data: Map<String, Any>? = null,
    func: (T.() -> Unit)? = null
): T {
    val dialog = type.newInstance().apply { func?.let { it() } }
    data?.let {
        dialog.setData(it as HashMap<String, Any>)
    }
    hideFragmentByTag(type.simpleName)
    dialog.show(childFragmentManager, type.simpleName)
    return dialog
}

fun DialogFragment.isShowing() = (dialog?.isShowing == true)
