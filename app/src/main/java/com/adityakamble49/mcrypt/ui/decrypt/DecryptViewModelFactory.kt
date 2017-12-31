package com.adityakamble49.mcrypt.ui.decrypt

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.adityakamble49.mcrypt.interactor.GetEncryptedTextFromFileUseCase

/**
 * Decrypt ViewModel Factory
 *
 * @author Aditya Kamble
 * @since 31/12/2017
 */
class DecryptViewModelFactory(
        private val getEncryptedTextFromFileUseCase: GetEncryptedTextFromFileUseCase) :
        ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DecryptViewModel::class.java)) {
            return DecryptViewModel(getEncryptedTextFromFileUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}