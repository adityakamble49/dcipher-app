package com.adityakamble49.mcrypt.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.adityakamble49.mcrypt.db.RSAKeyPairRepo

/**
 * KeyManager ViewModel Factory
 *
 * @author Aditya Kamble
 * @since 13/12/2017
 */
class KeyManagerViewModelFactory(
        private val rsaKeyPairRepo: RSAKeyPairRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(KeyManagerViewModel::class.java)) {
            return KeyManagerViewModel(rsaKeyPairRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}