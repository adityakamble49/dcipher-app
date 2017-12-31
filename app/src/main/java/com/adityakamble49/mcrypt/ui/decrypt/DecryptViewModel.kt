package com.adityakamble49.mcrypt.ui.decrypt

import android.arch.lifecycle.ViewModel
import android.net.Uri
import com.adityakamble49.mcrypt.interactor.DecryptTextUseCase
import com.adityakamble49.mcrypt.interactor.GetEncryptedTextFromFileUseCase
import com.adityakamble49.mcrypt.model.EncryptionKey
import io.reactivex.SingleObserver
import javax.inject.Inject

/**
 * View Model for Decrypt
 *
 * @author Aditya Kamble
 * @since 31/12/2017
 */
class DecryptViewModel @Inject constructor(
        private val getEncryptedTextFromFileUseCase: GetEncryptedTextFromFileUseCase,
        private val decryptTextUseCase: DecryptTextUseCase) :
        ViewModel() {

    fun getEncryptedTextFromFile(uri: Uri, observer: SingleObserver<String>) {
        getEncryptedTextFromFileUseCase.execute(uri).subscribe(observer)
    }

    fun decryptText(encryptionKey: EncryptionKey, encryptedText: String,
                    observer: SingleObserver<String>) {
        decryptTextUseCase.execute(encryptionKey, encryptedText).subscribe(observer)
    }
}