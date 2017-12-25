package com.adityakamble49.mcrypt.interactor

import com.adityakamble49.mcrypt.cache.PreferenceHelper
import com.adityakamble49.mcrypt.cache.db.EncryptionKeyRepo
import com.adityakamble49.mcrypt.model.EncryptionKey
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.CompletableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Delete [[EncryptionKey]] UseCase
 * Delete [[EncryptionKey]] if not in use
 * @author Aditya Kamble
 * @since 24/12/2017
 */
class DeleteEncryptionKeyUseCase @Inject constructor(
        private val encryptionKeyRepo: EncryptionKeyRepo,
        private val preferenceHelper: PreferenceHelper) {

    private fun buildUseCaseObservable(encryptionKey: EncryptionKey): Completable {
        return Completable.create(object : CompletableOnSubscribe {
            override fun subscribe(e: CompletableEmitter) {
                val currentKeyId = preferenceHelper.currentEncryptionKeyId
                if (currentKeyId != encryptionKey.id) {
                    encryptionKeyRepo.deleteEncryptionKey(encryptionKey)
                    e.onComplete()
                } else {
                    e.onError(EncryptionKeyInUseException("Key In Use! Can't be deleted"))
                }
            }
        })
    }

    fun execute(encryptionKey: EncryptionKey): Completable {
        return buildUseCaseObservable(encryptionKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}