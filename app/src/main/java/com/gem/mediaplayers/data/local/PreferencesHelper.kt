package com.gem.mediaplayers.data.local

import com.gem.mediaplayers.data.model.User

interface PreferencesHelper {
    fun getCurrentUser(): User?
    fun setCurrentUser(user: User?)
}