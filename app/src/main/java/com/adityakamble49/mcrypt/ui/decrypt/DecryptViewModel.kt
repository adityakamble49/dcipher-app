package com.adityakamble49.mcrypt.ui.decrypt

import android.arch.lifecycle.ViewModel
import android.net.Uri
import com.adityakamble49.mcrypt.interactor.GetEncryptedTextFromFileUseCase
import io.reactivex.SingleObserver
import javax.inject.Inject

/**
 * View Model for Decrypt
 *
 * @author Aditya Kamble
 * @since 31/12/2017
 */
class DecryptViewModel @Inject constructor(
        private val getEncryptedTextFromFileUseCase: GetEncryptedTextFromFileUseCase) :
        ViewModel() {

    fun getEncryptedTextFromFile(uri: Uri, observer: SingleObserver<String>) {
        getEncryptedTextFromFileUseCase.execute(uri).subscribe(observer)
    }
}