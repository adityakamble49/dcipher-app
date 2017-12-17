package com.adityakamble49.mcrypt.interactor

import com.adityakamble49.mcrypt.cache.db.RSAKeyPairRepo
import com.adityakamble49.mcrypt.model.RSAKeyPair
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.CompletableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Save RSAKeyPair UseCase
 *
 * @author Aditya Kamble
 * @since 17/12/2017
 */
class SaveRSAKeyPairUseCase @Inject constructor(
        private val rsaKeyPairRepo: RSAKeyPairRepo) {

    private fun buildUseCaseObservable(rsaKeyPair: RSAKeyPair): Completable {
        return Completable.create(object : CompletableOnSubscribe {
            override fun subscribe(e: CompletableEmitter) {
                rsaKeyPairRepo.insertRSAKeyPair(rsaKeyPair)
                e.onComplete()
            }
        })
    }

    fun execute(rsaKeyPair: RSAKeyPair): Completable {
        return buildUseCaseObservable(rsaKeyPair)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}