package com.adityakamble49.dcipher.interactor

import com.adityakamble49.dcipher.model.EncryptionKey
import com.adityakamble49.dcipher.utils.EncryptionManager
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Build [[EncryptionKey]] UseCase
 *
 * @author Aditya Kamble
 * @since 17/12/2017
 */
class BuildEncryptionKeyUseCase @Inject constructor(
        private val encryptionManager: EncryptionManager) {

    private fun buildUseCaseObservable(keyName: String): Observable<EncryptionKey> {
        return Observable.create(object : ObservableOnSubscribe<EncryptionKey> {
            override fun subscribe(e: ObservableEmitter<EncryptionKey>) {
                val generatedKey = encryptionManager.buildKeyPair()
                val rsaKeyPair = EncryptionKey(0, keyName, generatedKey.encryptedSessionKey,
                        generatedKey.publicKey, generatedKey.privateKey)
                e.onNext(rsaKeyPair)
                e.onComplete()
            }
        })
    }

    fun execute(keyName: String): Observable<EncryptionKey> {
        return buildUseCaseObservable(keyName)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }
}