package com.adityakamble49.mcrypt.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.adityakamble49.mcrypt.R
import com.adityakamble49.mcrypt.model.RSAKeyPair
import com.adityakamble49.mcrypt.ui.common.CommonViewModel
import com.adityakamble49.mcrypt.ui.common.CommonViewModelFactory
import dagger.android.AndroidInjection
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_encrypt.*
import javax.inject.Inject

class EncryptActivity : AppCompatActivity() {

    // Dagger Injected Fields
    @Inject lateinit var commonViewModelFactory: CommonViewModelFactory

    // ViewModel
    private lateinit var commonViewModel: CommonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encrypt)

        AndroidInjection.inject(this)

        // Get Key Manager ViewModel from Factory
        commonViewModel = ViewModelProviders.of(this, commonViewModelFactory).get(
                CommonViewModel::class.java)

        bindView()
    }

    override fun onResume() {
        super.onResume()
        commonViewModel.requestCurrentRSAKeyPair(GetCurrentRSAKeyPairSubscriber())
    }

    /*
     * Helper Functions
     */

    private fun bindView() {

    }

    private inner class GetCurrentRSAKeyPairSubscriber : Observer<RSAKeyPair> {
        override fun onSubscribe(d: Disposable) {}

        override fun onNext(t: RSAKeyPair) {
            val loadedKey = getString(R.string.loaded_key_placeholder, t.name)
            loaded_key.text = loadedKey
        }

        override fun onComplete() {}

        override fun onError(e: Throwable) {}
    }
}
