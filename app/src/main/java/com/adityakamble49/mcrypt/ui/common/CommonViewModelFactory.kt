package com.adityakamble49.mcrypt.ui.common

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.adityakamble49.mcrypt.interactor.GetCurrentRSAKeyPairUseCase

/**
 * Common ViewModel Factory
 *
 * @author Aditya Kamble
 * @since 17/12/2017
 */
class CommonViewModelFactory(
        private val getCurrentRSAKeyPairUseCase: GetCurrentRSAKeyPairUseCase) :
        ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommonViewModel::class.java)) {
            return CommonViewModel(getCurrentRSAKeyPairUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}