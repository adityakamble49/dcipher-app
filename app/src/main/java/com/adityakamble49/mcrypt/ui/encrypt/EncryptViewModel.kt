package com.adityakamble49.mcrypt.ui.encrypt

import android.arch.lifecycle.ViewModel
import com.adityakamble49.mcrypt.interactor.EncryptTextUseCase
import com.adityakamble49.mcrypt.model.EncryptionKey
import io.reactivex.SingleObserver
import javax.inject.Inject

/**
 * View Model for Encrypt
 *
 * @author Aditya Kamble
 * @since 25/12/2017
 */
class EncryptViewModel @Inject constructor(
        private val encryptTextUseCase: EncryptTextUseCase) : ViewModel() {

    fun encryptText(encryptionKey: EncryptionKey, textToEncrypt: String,
                    observer: SingleObserver<String>) {
        encryptTextUseCase.execute(encryptionKey, textToEncrypt).subscribe(observer)
    }
}