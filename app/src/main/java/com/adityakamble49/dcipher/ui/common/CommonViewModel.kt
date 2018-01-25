package com.adityakamble49.dcipher.ui.common

import android.arch.lifecycle.ViewModel
import com.adityakamble49.dcipher.interactor.GetCurrentEncryptionKeyUseCase
import com.adityakamble49.dcipher.model.EncryptionKey
import io.reactivex.Observer
import javax.inject.Inject

/**
 * Common View Model for DCipher
 *
 * @author Aditya Kamble
 * @since 17/12/2017
 */
class CommonViewModel @Inject constructor(
        private val getCurrentEncryptionKeyUseCase: GetCurrentEncryptionKeyUseCase) : ViewModel() {

    fun requestCurrentEncryptionKey(observer: Observer<EncryptionKey>) {
        getCurrentEncryptionKeyUseCase.execute().subscribe(observer)
    }
}