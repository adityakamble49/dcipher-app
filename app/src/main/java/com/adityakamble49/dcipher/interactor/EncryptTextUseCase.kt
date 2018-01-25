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
 * Encrypt Text using [[EncryptionKey]]
 *
 * @author Aditya Kamble
 * @since 25/12/2017
 */
class EncryptTextUseCase @Inject constructor(
        private val encryptionManager: EncryptionManager) {

    private fun buildUseCaseObservable(encryptionKey: EncryptionKey,
                                       textToEncrypt: String): Single<String> {
        return Single.create(object : SingleOnSubscribe<String> {
            override fun subscribe(e: SingleEmitter<String>) {
                val encryptedText = encryptionManager.encryptText(encryptionKey, textToEncrypt)
                e.onSuccess(encryptedText)
            }
        })
    }

    fun execute(encryptionKey: EncryptionKey, textToEncrypt: String): Single<String> {
        return buildUseCaseObservable(encryptionKey, textToEncrypt)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }
}