package com.adityakamble49.mcrypt.ui.common

import android.arch.lifecycle.ViewModel
import com.adityakamble49.mcrypt.interactor.GetCurrentEncryptionKeyUseCase
import com.adityakamble49.mcrypt.model.EncryptionKey
import io.reactivex.Observer
import javax.inject.Inject

/**
 * Common View Model for MCrypt
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