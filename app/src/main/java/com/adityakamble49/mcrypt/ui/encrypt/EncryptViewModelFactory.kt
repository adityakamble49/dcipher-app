package com.adityakamble49.mcrypt.ui.encrypt

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.adityakamble49.mcrypt.interactor.EncryptTextUseCase
import com.adityakamble49.mcrypt.interactor.GetTextFromFileUseCase
import com.adityakamble49.mcrypt.interactor.SaveEncryptedTextToFileUseCase

/**
 * Encrypt ViewModel Factory
 *
 * @author Aditya Kamble
 * @since 25/12/2017
 */
class EncryptViewModelFactory(
        private val encryptTextUseCase: EncryptTextUseCase,
        private val saveEncryptedTextToFileUseCase: SaveEncryptedTextToFileUseCase,
        private val getTextFromFileUseCase: GetTextFromFileUseCase) :
        ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EncryptViewModel::class.java)) {
            return EncryptViewModel(encryptTextUseCase, saveEncryptedTextToFileUseCase, getTextFromFileUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}