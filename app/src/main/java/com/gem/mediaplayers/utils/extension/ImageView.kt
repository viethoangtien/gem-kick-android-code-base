package com.gem.mediaplayers.utils.extension

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.gem.mediaplayers.R
import com.gem.mediaplayers.data.enum.GlideScaleType

fun ImageView.loadImage(
    context: Context,
    image: Any?,
    @DrawableRes placeholderRes: Int = R.drawable.img_placeholder,
    glideScaleType: GlideScaleType = GlideScaleType.CENTER_CROP,
    skipMemoryCache: Boolean = false,
    isUsingScaleType: Boolean = true
) {
    val placeholder = ContextCompat.getDrawable(context, placeholderRes)
    if (image != null) {
        val scaleType = when (glideScaleType) {
            GlideScaleType.CENTER_CROP -> CenterCrop()
            GlideScaleType.CENTER_INSIDE -> CenterInside()
            GlideScaleType.FIT_CENTER -> FitCenter()
        }
        if (isUsingScaleType) {
            Glide.with(context)
                .load(image)
                .transform(scaleType)
                .placeholder(R.color.transparent)
                .skipMemoryCache(skipMemoryCache)
                .error(placeholderRes)
                .into(this)
        } else {
            Glide.with(context)
                .load(image)
                .placeholder(R.color.transparent)
                .skipMemoryCache(skipMemoryCache)
                .error(placeholderRes)
                .into(this)
        }
    } else if (placeholder != null) {
        this.setImageDrawable(placeholder)
    }
}

fun ImageView.loadImageAsGif(
    context: Context,
    image: Any?
) {
    Glide.with(context)
        .asGif()
        .load(image)
        .centerCrop()
        .into(this)

}