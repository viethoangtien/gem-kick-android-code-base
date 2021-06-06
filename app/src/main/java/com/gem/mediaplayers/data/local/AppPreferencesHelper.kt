package com.gem.mediaplayers.data.local

import android.content.Context
import android.content.SharedPreferences
import com.gem.mediaplayers.data.model.User
import com.gem.mediaplayers.di.PreferenceInfo
import com.google.gson.Gson
import javax.inject.Inject

class AppPreferencesHelper @Inject constructor(
    context: Context,
    @PreferenceInfo prefFileName: String,
    val gson: Gson
) : PreferencesHelper {

    private var mPrefs: SharedPreferences =
        context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    override fun getCurrentUser(): User? {
        val str = mPrefs.getString(PREF_KEY_CURRENT_USER, "")
        return if (str != null) gson.fromJson(str, User::class.java)
        else null
    }

    override fun setCurrentUser(user: User?) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER, gson.toJson(user)).apply()
    }

    companion object {
        const val PREF_KEY_CURRENT_USER = "PREF_KEY_CURRENT_USER"
    }

}