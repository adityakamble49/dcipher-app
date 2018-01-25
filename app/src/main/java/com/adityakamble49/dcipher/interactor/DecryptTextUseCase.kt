package com.adityakamble49.dcipher.interactor

import com.adityakamble49.dcipher.model.EncryptionKey
import com.adityakamble49.dcipher.utils.EncryptionManager
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Decrypt Text using [[EncryptionKey]]
 *
 * @author Aditya Kamble
 * @since 31/12/2017
 */
class DecryptTextUseCase @Inject constructor(
        private val encryptionManager: EncryptionManager) {

    private fun buildUseCaseObservable(encryptionKey: EncryptionKey,
                                       textToDecrypt: String): Single<String> {
        return Single.create(object : SingleOnSubscribe<String> {
            override fun subscribe(e: SingleEmitter<String>) {
                val decryptedText = encryptionManager.decryptText(encryptionKey, textToDecrypt)
                e.onSuccess(decryptedText)
            }
        })
    }

    fun execute(encryptionKey: EncryptionKey, textToDecrypt: String): Single<String> {
        return buildUseCaseObservable(encryptionKey, textToDecrypt)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }
}