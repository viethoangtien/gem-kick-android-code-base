package com.gem.mediaplayers.base.ui

/**
 * Created by taind-201 on 3/23/2020.
 */
interface ServerErrorListener {

    fun handleError(title: String? = null, throwable: Throwable, isFinish : Boolean? = false)

    fun handleShowMessage(message : String, isFinish : Boolean? = false)

}