package com.adityakamble49.dcipher.interactor

import com.adityakamble49.dcipher.cache.db.EncryptionKeyRepo
import com.adityakamble49.dcipher.model.EncryptionKey
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.CompletableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Save [[EncryptionKey]] UseCase
 *
 * @author Aditya Kamble
 * @since 17/12/2017
 */
class SaveEncryptionKeyUseCase @Inject constructor(
        private val encryptionKeyRepo: EncryptionKeyRepo) {

    private fun buildUseCaseObservable(encryptionKey: EncryptionKey): Completable {
        return Completable.create(object : CompletableOnSubscribe {
            override fun subscribe(e: CompletableEmitter) {
                encryptionKey.id = 0
                encryptionKeyRepo.insertEncryptionKey(encryptionKey)
                e.onComplete()
            }
        })
    }

    fun execute(encryptionKey: EncryptionKey): Completable {
        return buildUseCaseObservable(encryptionKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}