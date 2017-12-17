package com.adityakamble49.mcrypt.ui.keys

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.adityakamble49.mcrypt.cache.db.RSAKeyPairRepo
import com.adityakamble49.mcrypt.interactor.BuildRSAKeyPairUseCase
import com.adityakamble49.mcrypt.interactor.SaveRSAKeyPairUseCase
import com.adityakamble49.mcrypt.model.RSAKeyPair
import io.reactivex.CompletableObserver
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
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
    lateinit var saveRSAKeyObserver: CompletableObserver

    fun generateAndSaveKeyPair(keyName: String, observer: CompletableObserver) {
        saveRSAKeyObserver = observer
        buildRSAKeyPairUseCase.execute(keyName).subscribe(BuildRSAKeyPairSubscriber())
    }

    inner class BuildRSAKeyPairSubscriber : Observer<RSAKeyPair> {
        override fun onSubscribe(d: Disposable) {}

        override fun onNext(t: RSAKeyPair) {
            saveRSAKeyPairUseCase.execute(t).subscribe(saveRSAKeyObserver)
        }

        override fun onComplete() {}

        override fun onError(e: Throwable) {}
    }
}