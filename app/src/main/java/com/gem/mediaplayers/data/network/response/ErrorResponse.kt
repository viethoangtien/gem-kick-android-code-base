package com.gem.mediaplayers.data.network.response

import com.google.gson.annotations.SerializedName


data class ErrorResponse(

    @SerializedName("status")
    var status: Int,

    @SerializedName("message")
    val message: String

)