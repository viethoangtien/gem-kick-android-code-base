package com.gem.mediaplayers.utils.extension

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Point
import android.graphics.PorterDuff
import android.graphics.Rect
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.webkit.WebView
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.gem.mediaplayers.base.ui.BaseActivity
import com.google.android.material.appbar.AppBarLayout

const val CLICK_THROTTLE_DELAY = 600L
const val CLICK_THROTTLE_DELAY_GAME = 300L
const val COLOR_FILTER_BLACK_WHITE = 0
const val COLOR_FILTER_GRAY = 1


fun View.visible() {
    visibility = VISIBLE
}

fun View.gone() {
    visibility = GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.goneUnless(visible: Boolean) {
    visibility = if (visible) VISIBLE else GONE
}

fun View.invisibleUnless(visible: Boolean) {
    visibility = if (visible) VISIBLE else GONE
}

fun View.loadAnimation(context: Context, anim: Int) {
    startAnimation(AnimationUtils.loadAnimation(context, anim))
}

fun WebView.loadFromHtmlString(content: String?) {
    this.loadDataWithBaseURL(null, content, "text/html; charset=utf-8", "utf-8", null)
}

fun View.avoidDoubleClick(delay: Long = CLICK_THROTTLE_DELAY, onClick: (View) -> Unit) {
    setOnClickListener {
        onClick(it)
        isClickable = false
        postDelayed({
            isClickable = true
        }, delay)
    }
}

fun ImageView.loadImageAsGif(
    context: Context,
    image: Any?,
    isFlashCardGif: Boolean = false
) {
    if (isFlashCardGif) {
        Glide.with(context)
            .asGif()
            .load(image)
            .fitCenter()
            .into(this)

    } else {
        Glide.with(context)
            .asGif()
            .load(image)
            .centerCrop()
            .into(this)
    }

}

fun ImageView.tint(
    context: Context,
    color: Int,
    tintMode: PorterDuff.Mode = PorterDuff.Mode.MULTIPLY
) {
    setColorFilter(
        ContextCompat.getColor(context, color),
        tintMode
    )
}

fun ImageView.removeTint() {
    clearColorFilter()
}

fun View.flipAnimation(func: (() -> Unit)? = null) {
    val oa1 = ObjectAnimator.ofFloat(this, "scaleX", 1f, 0f)
    val oa2 = ObjectAnimator.ofFloat(this, "scaleX", 0f, 1f)
    oa1.interpolator = DecelerateInterpolator()
    oa2.interpolator = AccelerateDecelerateInterpolator()
    oa1.duration = 200
    oa2.duration = 200
    oa1.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            oa2.start()
        }
    })
    oa2.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            super.onAnimationEnd(animation)
            func?.invoke()
        }
    })
    oa1.start()
}

fun AppBarLayout.disableScroll() {
    val params: CoordinatorLayout.LayoutParams = layoutParams as CoordinatorLayout.LayoutParams
    val behavior = params.behavior as AppBarLayout.Behavior
    behavior.setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
        override fun canDrag(@NonNull appBarLayout: AppBarLayout): Boolean {
            return false
        }
    })
}

fun View.getLocationOnScreen(): Point {
    val location = IntArray(2)
    this.getLocationOnScreen(location)
    return Point(location[0], location[1])
}

fun View.getLocationView(parentView: ViewGroup): Rect {
    val offsetViewBounds = Rect()
    //returns the visible bounds
    getDrawingRect(offsetViewBounds)
    // calculates the relative coordinates to the parent
    parentView.offsetDescendantRectToMyCoords(this, offsetViewBounds)
    return offsetViewBounds
}

fun Context.getStatusBarHeight(): Int {
    var result = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelSize(resourceId)
    }
    return result
}


fun View.setPaddings(padding: Int) {
    setPadding(
        padding,
        padding,
        padding,
        padding
    )
}