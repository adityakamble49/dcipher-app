package com.adityakamble49.mcrypt.ui.common

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.adityakamble49.mcrypt.cache.PreferenceHelper
import com.adityakamble49.mcrypt.cache.db.RSAKeyPairRepo
import com.adityakamble49.mcrypt.model.RSAKeyPair
import javax.inject.Inject

/**
 * Common View Model for MCrypt
 *
 * @author Aditya Kamble
 * @since 17/12/2017
 */
class CommonViewModel @Inject constructor(
        private val rsaKeyPairRepo: RSAKeyPairRepo,
        private val preferenceHelper: PreferenceHelper) : ViewModel() {

    lateinit var rsaKeyPair: LiveData<RSAKeyPair>

    fun getCurrentRSAKeyPair(): RSAKeyPair {
        return rsaKeyPairRepo.getRSAKeyPairById(preferenceHelper.currentRSAKeyId)
    }
}