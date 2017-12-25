package com.adityakamble49.mcrypt.interactor

import com.adityakamble49.mcrypt.cache.PreferenceHelper
import com.adityakamble49.mcrypt.cache.db.EncryptionKeyRepo
import com.adityakamble49.mcrypt.cache.exception.EncryptionKeyNotFoundException
import com.adityakamble49.mcrypt.model.EncryptionKey
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
 * @since 18/12/2017
 */
class GetCurrentEncryptionKeyUseCase @Inject constructor(
        private val encryptionKeyRepo: EncryptionKeyRepo,
        private val preferenceHelper: PreferenceHelper) {

    private fun buildUseCaseObservable(): Observable<EncryptionKey> {
        return Observable.create(object : ObservableOnSubscribe<EncryptionKey> {
            override fun subscribe(e: ObservableEmitter<EncryptionKey>) {
                val currentKeyId = preferenceHelper.currentEncryptionKeyId
                val encryptionKey = encryptionKeyRepo.getEncryptionKeyById(currentKeyId)
                if (encryptionKey != null) {
                    e.onNext(encryptionKey)
                    e.onComplete()
                } else {
                    e.onError(EncryptionKeyNotFoundException(
                            "RSA Key not found with id $currentKeyId"))
                }
            }
        })
    }

    fun execute(): Observable<EncryptionKey> {
        return buildUseCaseObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}