package com.adityakamble49.dcipher.cache

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
        private val PREF_DCIPHER_PACKAGE_NAME = "com.adityakamble49.dcipher"

        private val PREF_KEY_CURRENT_ENCRYPTION_KEY_ID = "current_encryption_key_id"
        val DEFAULT_CURRENT_ENCRYPTION_KEY_ID = 0
    }

    private val dCipherPref: SharedPreferences

    init {
        dCipherPref = context.getSharedPreferences(PREF_DCIPHER_PACKAGE_NAME, Context.MODE_PRIVATE)
    }

    var currentEncryptionKeyId: Int
        get() = dCipherPref.getInt(PREF_KEY_CURRENT_ENCRYPTION_KEY_ID,
                DEFAULT_CURRENT_ENCRYPTION_KEY_ID)
        set(currentKeyId) = dCipherPref.edit().putInt(PREF_KEY_CURRENT_ENCRYPTION_KEY_ID,
                currentKeyId).apply()
}