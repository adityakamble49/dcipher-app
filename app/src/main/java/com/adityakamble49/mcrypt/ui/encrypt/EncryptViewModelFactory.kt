package com.adityakamble49.mcrypt.ui.encrypt

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.adityakamble49.mcrypt.interactor.EncryptTextUseCase

/**
 * Encrypt ViewModel Factory
 *
 * @author Aditya Kamble
 * @since 25/12/2017
 */
class EncryptViewModelFactory(
        private val encryptTextUseCase: EncryptTextUseCase) :
        ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EncryptViewModel::class.java)) {
            return EncryptViewModel(encryptTextUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}