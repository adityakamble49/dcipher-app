package com.adityakamble49.mcrypt.ui.common

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.adityakamble49.mcrypt.interactor.GetCurrentEncryptionKeyUseCase

/**
 * Common ViewModel Factory
 *
 * @author Aditya Kamble
 * @since 17/12/2017
 */
class CommonViewModelFactory(
        private val getCurrentEncryptionKeyUseCase: GetCurrentEncryptionKeyUseCase) :
        ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommonViewModel::class.java)) {
            return CommonViewModel(getCurrentEncryptionKeyUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}