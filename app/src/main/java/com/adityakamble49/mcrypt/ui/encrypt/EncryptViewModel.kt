package com.adityakamble49.mcrypt.ui.encrypt

import android.arch.lifecycle.ViewModel
import android.net.Uri
import com.adityakamble49.mcrypt.interactor.EncryptTextUseCase
import com.adityakamble49.mcrypt.interactor.GetTextFromFileUseCase
import com.adityakamble49.mcrypt.interactor.SaveEncryptedTextToFileUseCase
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
        private val encryptTextUseCase: EncryptTextUseCase,
        private val saveEncryptedTextToFileUseCase: SaveEncryptedTextToFileUseCase,
        private val getTextFromFileUseCase: GetTextFromFileUseCase) : ViewModel() {

    fun encryptText(encryptionKey: EncryptionKey, textToEncrypt: String,
                    observer: SingleObserver<String>) {
        encryptTextUseCase.execute(encryptionKey, textToEncrypt).subscribe(observer)
    }

    fun saveEncryptedTextToFile(encryptedFileName: String, encryptedText: String,
                                observer: SingleObserver<Uri>) {
        saveEncryptedTextToFileUseCase.execute(encryptedFileName, encryptedText).subscribe(observer)
    }

    fun getTextFromFile(uri: Uri, observer: SingleObserver<String>) {
        getTextFromFileUseCase.execute(uri).subscribe(observer)
    }
}