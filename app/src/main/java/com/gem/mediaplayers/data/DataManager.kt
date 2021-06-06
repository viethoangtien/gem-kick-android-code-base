package com.gem.mediaplayers.data

import com.gem.mediaplayers.data.local.PreferencesHelper
import com.gem.mediaplayers.data.model.*


interface DataManager : PreferencesHelper {

    fun updateUserInfo(user: User?)

    fun setUserAsLoggedOut()

    fun updateApiHeader(userId: Long?, token: String?)


}
