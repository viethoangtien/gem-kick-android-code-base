package com.gem.mediaplayers.data

import android.content.Context
import com.gem.mediaplayers.data.local.PreferencesHelper
import com.gem.mediaplayers.data.model.*
import com.gem.mediaplayers.data.network.ApiHelper
import com.gem.mediaplayers.data.network.exception.KickException
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
@Singleton
class AppDataManager @Inject constructor(
    val context: Context,
    private val preferencesHelper: PreferencesHelper,
    private val apiHelper: ApiHelper,
    val gson: Gson
) : DataManager {

    override fun updateApiHeader(userId: Long?, token: String?) {
        apiHelper.getAppHeader().authHeader.userId = userId
        apiHelper.getAppHeader().authHeader.token = token
    }

    override fun updateUserInfo(user: User?) {

    }

    override fun setUserAsLoggedOut() {
        updateUserInfo(null)
    }

    override fun getCurrentUser(): User? {
        return preferencesHelper.getCurrentUser()
    }

    override fun setCurrentUser(user: User?) {
        preferencesHelper.setCurrentUser(user)
    }

    private fun <T> handleData(res: IResponse<T>): T? {
        if (res.status == 200 && res.data == null) {
            if (res is BaseResponse) return "" as T
        }
        return when (res.status) {
            200 -> res.data
            else -> {
                val exception = KickException(res.message)
                exception.statusCode = res.status
                throw exception
            }
        }
    }
}