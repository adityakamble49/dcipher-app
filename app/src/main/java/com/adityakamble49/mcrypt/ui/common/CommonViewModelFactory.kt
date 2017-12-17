package com.adityakamble49.mcrypt.ui.common

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.adityakamble49.mcrypt.cache.PreferenceHelper
import com.adityakamble49.mcrypt.cache.db.RSAKeyPairRepo

/**
 * Common ViewModel Factory
 *
 * @author Aditya Kamble
 * @since 17/12/2017
 */
class CommonViewModelFactory(
        private val rsaKeyPairRepo: RSAKeyPairRepo,
        private val preferenceHelper: PreferenceHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommonViewModel::class.java)) {
            return CommonViewModel(rsaKeyPairRepo, preferenceHelper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}