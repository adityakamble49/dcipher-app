package com.adityakamble49.mcrypt.ui.keys

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.adityakamble49.mcrypt.db.RSAKeyPairRepo
import com.adityakamble49.mcrypt.interactor.BuildRSAKeyPairUseCase
import com.adityakamble49.mcrypt.interactor.SaveRSAKeyPairUseCase
import com.adityakamble49.mcrypt.model.RSAKeyPair
import io.reactivex.CompletableObserver
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

/**
 * View Model for Key Manager Activity
 *
 * @author Aditya Kamble
 * @since 13/12/2017
 */
class KeyManagerViewModel @Inject constructor(
        private val rsaKeyPairRepo: RSAKeyPairRepo,
        private val buildRSAKeyPairUseCase: BuildRSAKeyPairUseCase,
        private val saveRSAKeyPairUseCase: SaveRSAKeyPairUseCase) : ViewModel() {

    val rsaKeyPairList: LiveData<List<RSAKeyPair>> = rsaKeyPairRepo.getRSAKeyPairList()

    fun generateAndSaveKeyPair(keyName: String) {
        buildRSAKeyPairUseCase.execute(keyName).subscribe(BuildRSAKeyPairSubscriber())
    }

    inner class BuildRSAKeyPairSubscriber : Observer<RSAKeyPair> {
        override fun onSubscribe(d: Disposable) {}

        override fun onNext(t: RSAKeyPair) {
            saveRSAKeyPairUseCase.execute(t).subscribe(SaveKeyPairSubscriber())
        }

        override fun onComplete() {}

        override fun onError(e: Throwable) {}
    }

    inner class SaveKeyPairSubscriber : CompletableObserver {
        override fun onComplete() {
            Timber.i("RSA Key Saved")
        }

        override fun onSubscribe(d: Disposable) {}

        override fun onError(e: Throwable) {}
    }
}