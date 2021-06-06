package com.gem.mediaplayers.data.network

import com.gem.mediaplayers.data.network.header.ApiHeader

interface ApiHelper {
    fun getAppHeader(): ApiHeader
}