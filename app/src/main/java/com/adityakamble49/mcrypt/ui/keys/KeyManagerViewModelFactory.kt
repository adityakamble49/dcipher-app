package com.adityakamble49.mcrypt.ui.keys

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.adityakamble49.mcrypt.db.RSAKeyPairRepo
import com.adityakamble49.mcrypt.interactor.BuildRSAKeyPairUseCase
import com.adityakamble49.mcrypt.interactor.SaveRSAKeyPairUseCase

/**
 * KeyManager ViewModel Factory
 *
 * @author Aditya Kamble
 * @since 13/12/2017
 */
class KeyManagerViewModelFactory(
        private val rsaKeyPairRepo: RSAKeyPairRepo,
        private val buildRSAKeyPairUseCase: BuildRSAKeyPairUseCase,
        private val saveRSAKeyPairUseCase: SaveRSAKeyPairUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(KeyManagerViewModel::class.java)) {
            return KeyManagerViewModel(rsaKeyPairRepo, buildRSAKeyPairUseCase,
                    saveRSAKeyPairUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}