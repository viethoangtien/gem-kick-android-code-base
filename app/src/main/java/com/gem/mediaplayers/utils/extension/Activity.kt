package com.gem.mediaplayers.utils.extension

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 * Extension method to startActivity for Context.
 */

inline fun <reified T : Any> Context?.launchActivity() =
    this?.startActivity(Intent(this, T::class.java))

inline fun <reified T : Any> Context?.launchActivity(func: Intent.() -> Intent) =
    this?.startActivity(Intent(this, T::class.java).func())

inline fun <reified T : Any> AppCompatActivity.launchActivityForResult(
    func: Intent.() -> Intent,
    requestCode: Int = -1
) =
    startActivityForResult(Intent(this, T::class.java).func(), requestCode)

inline fun <reified T : Any> Fragment.launchActivityForResult(
    requestCode: Int = -1
) =
    startActivityForResult(Intent(this.requireActivity(), T::class.java), requestCode)

inline fun <reified T : Any> AppCompatActivity.launchActivityForResult(
    requestCode: Int = -1
) =
    startActivityForResult(Intent(this, T::class.java), requestCode)

fun AppCompatActivity.hideFragmentByTag(tag: String) {
    val ft = supportFragmentManager.beginTransaction()
    val frag = supportFragmentManager.findFragmentByTag(tag)
    frag?.let {
        ft.remove(it)
    }
    ft.addToBackStack(null)
}

fun dagger.android.support.DaggerFragment.hideFragmentByTag(tag: String) {
    val ft = childFragmentManager.beginTransaction()
    val frag = childFragmentManager.findFragmentByTag(tag)
    frag?.let {
        ft.remove(it)
    }
    ft.addToBackStack(null)
}
