package com.gem.mediaplayers.data.model

import android.content.Context
import androidx.room.Entity
import com.google.firebase.crashlytics.internal.common.CommonUtils
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["userId"])
data class User(
    @SerializedName("createdOn")
    @Expose
    var createdOn: String? = null,

    @SerializedName("displayName")
    @Expose
    var displayName: String? = null,

    @SerializedName("dateOfBirth")
    @Expose
    var dateOfBirth: String? = null,

    @SerializedName("skillLevel")
    @Expose
    var skillLevel: String? = null,

    @SerializedName("email")
    @Expose
    var email: String? = null,

    @SerializedName("emailVerified")
    @Expose
    var emailVerified: Boolean? = null,

    @SerializedName("firstLogin")
    @Expose
    var firstLogin: Boolean? = null,

    @SerializedName("firstName")
    @Expose
    var firstName: String? = null,

    @SerializedName("id")
    @Expose
    var userId: Long? = null,

    @SerializedName("imgUrl")
    @Expose
    var imgUrl: String? = null,

    @SerializedName("lang")
    @Expose
    var lang: String? = null,

    @SerializedName("lastName")
    @Expose
    var lastName: String? = null,

    @SerializedName("parent")
    @Expose
    var parent: Boolean? = null,

    @SerializedName("phone")
    @Expose
    var phone: String? = null,

    @SerializedName("referCode")
    @Expose
    var referCode: String? = null,

    @SerializedName("socialSource")
    @Expose
    var socialSource: String? = null,

    @SerializedName("subscriptionActiveDate")
    @Expose
    var subscriptionActiveDate: String? = null,

    @SerializedName("subscriptionExpiredDate")
    @Expose
    var subscriptionExpiredDate: String? = null,

    @SerializedName("token")
    @Expose
    var token: String? = null,

    @SerializedName("username")
    @Expose
    var username: String? = null,

    @SerializedName("showNotification")
    @Expose
    var showNotification: Boolean? = null,

    @SerializedName("vipViewCount")
    @Expose
    var vipViewCount: Int? = null,

    @SerializedName("affiliateManager")
    @Expose
    var affiliateManager: Boolean? = null
)