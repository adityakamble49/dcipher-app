package com.adityakamble49.mcrypt.interactor

import com.adityakamble49.mcrypt.cache.PreferenceHelper
import com.adityakamble49.mcrypt.cache.db.RSAKeyPairRepo
import com.adityakamble49.mcrypt.model.RSAKeyPair
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.CompletableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Delete RSAKeyPair UseCase
 * Delete RSAKeyPair if not in use
 * @author Aditya Kamble
 * @since 24/12/2017
 */
class DeleteRSAKeyPairUseCase @Inject constructor(
        private val rsaKeyPairRepo: RSAKeyPairRepo,
        private val preferenceHelper: PreferenceHelper) {

    private fun buildUseCaseObservable(rsaKeyPair: RSAKeyPair): Completable {
        return Completable.create(object : CompletableOnSubscribe {
            override fun subscribe(e: CompletableEmitter) {
                val currentKeyId = preferenceHelper.currentRSAKeyId
                if (currentKeyId != rsaKeyPair.id) {
                    rsaKeyPairRepo.deleteRSAKeyPair(rsaKeyPair)
                    e.onComplete()
                } else {
                    e.onError(RSAKeyPairInUseException("Key In Use! Can't be deleted"))
                }
            }
        })
    }

    fun execute(rsaKeyPair: RSAKeyPair): Completable {
        return buildUseCaseObservable(rsaKeyPair)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}