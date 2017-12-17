package com.adityakamble49.mcrypt.interactor

import com.adityakamble49.mcrypt.model.RSAKeyPair
import com.adityakamble49.mcrypt.utils.RSAEncryption
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Build RSAKeyPair UseCase
 *
 * @author Aditya Kamble
 * @since 17/12/2017
 */
class BuildRSAKeyPairUseCase @Inject constructor(
        private val rsaEncryption: RSAEncryption) {

    private fun buildUseCaseObservable(keyName: String): Observable<RSAKeyPair> {
        return Observable.create(object : ObservableOnSubscribe<RSAKeyPair> {
            override fun subscribe(e: ObservableEmitter<RSAKeyPair>) {
                val generatedKey = rsaEncryption.buildKeyPair()
                val rsaKeyPair = RSAKeyPair(0, keyName, generatedKey.public, generatedKey.private)
                e.onNext(rsaKeyPair)
                e.onComplete()
            }
        })
    }

    fun execute(keyName: String): Observable<RSAKeyPair> {
        return buildUseCaseObservable(keyName)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }
}