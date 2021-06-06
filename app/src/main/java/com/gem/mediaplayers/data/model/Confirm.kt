package com.gem.mediaplayers.data.model

import android.os.Parcelable
import com.gem.mediaplayers.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Confirm(
    var title: String?,
    var positiveTitleRes: Int = R.string.ok,
    var negativeTitleRes: Int?
) : Parcelable