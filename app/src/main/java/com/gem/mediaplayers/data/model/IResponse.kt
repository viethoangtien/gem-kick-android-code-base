package com.gem.mediaplayers.data.model

/**
 * Created by taind-201 on 4/18/2020.
 */
interface IResponse<T> {

    var status: Int

    val message: String

    val data: T?

}