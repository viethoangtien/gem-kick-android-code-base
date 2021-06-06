package com.gem.mediaplayers.data.network.header

import com.google.gson.annotations.SerializedName
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiHeader @Inject constructor(var authHeader: AuthHeader)

class AuthHeader @Inject constructor(

    @field:SerializedName("user_id")
    var userId: Long?,

    @field:SerializedName("token")
    var token: String?

)