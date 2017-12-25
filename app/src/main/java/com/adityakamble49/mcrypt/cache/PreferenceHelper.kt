package com.adityakamble49.mcrypt.cache

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

/**
 * Preference Helper
 *
 * @author Aditya Kamble
 * @since 17/12/2017
 */
class PreferenceHelper @Inject constructor(context: Context) {

    companion object {
        private val PREF_QUOTES_PACKAGE_NAME = "com.adityakamble49.mcrypt"

        private val PREF_KEY_CURRENT_ENCRYPTION_KEY_ID = "current_encryption_key_id"
    }

    private val mcryptPref: SharedPreferences

    init {
        mcryptPref = context.getSharedPreferences(PREF_QUOTES_PACKAGE_NAME, Context.MODE_PRIVATE)
    }

    var currentEncryptionKeyId: Int
        get() = mcryptPref.getInt(PREF_KEY_CURRENT_ENCRYPTION_KEY_ID, 0)
        set(currentKeyId) = mcryptPref.edit().putInt(PREF_KEY_CURRENT_ENCRYPTION_KEY_ID,
                currentKeyId).apply()
}