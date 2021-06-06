package com.gem.mediaplayers.data.model

import com.google.gson.annotations.SerializedName

data class BaseResponse(

    @SerializedName("status")
    override var status: Int,

    @SerializedName("message")
    override val message: String,

    @SerializedName("data")
    override val data: Any?

) : IResponse<Any>