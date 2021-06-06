package com.gem.mediaplayers.data.network.exception

class KickException(message: String?) : Exception(message) {

    var statusCode : Int = 0

}