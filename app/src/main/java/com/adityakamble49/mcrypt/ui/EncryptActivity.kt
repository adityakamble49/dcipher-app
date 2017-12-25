package com.adityakamble49.mcrypt.ui

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.adityakamble49.mcrypt.R
import com.adityakamble49.mcrypt.cache.exception.EncryptionKeyNotFoundException
import com.adityakamble49.mcrypt.model.EncryptionKey
import com.adityakamble49.mcrypt.ui.common.CommonViewModel
import com.adityakamble49.mcrypt.ui.common.CommonViewModelFactory
import com.adityakamble49.mcrypt.ui.keys.KeyManagerActivity
import com.adityakamble49.mcrypt.utils.EncryptionManager
import dagger.android.AndroidInjection
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_encrypt.*
import javax.inject.Inject

class EncryptActivity : AppCompatActivity(), View.OnClickListener {

    // Dagger Injected Fields
    @Inject lateinit var commonViewModelFactory: CommonViewModelFactory
    @Inject lateinit var encryptionManager: EncryptionManager

    // ViewModel
    private lateinit var commonViewModel: CommonViewModel

    // Other Fields
    var currentEncryptionKey: EncryptionKey? = null

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
        commonViewModel.requestCurrentEncryptionKey(GetCurrentEncryptionKeySubscriber())
    }

    /*
     * Listener Functions
     */

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.encrypt_button -> handleEncryptText()
            R.id.change_encryption_key -> startActivity(Intent(this, KeyManagerActivity::class.java))
        }
    }

    /*
     * Helper Functions
     */

    private fun bindView() {
        encrypt_button.setOnClickListener(this)
        change_encryption_key.setOnClickListener(this)
    }

    private inner class GetCurrentEncryptionKeySubscriber : Observer<EncryptionKey> {
        override fun onSubscribe(d: Disposable) {}

        override fun onNext(t: EncryptionKey) {
            currentEncryptionKey = t
            val loadedKey = getString(R.string.loaded_key_placeholder, t.name)
            loaded_key.text = loadedKey
        }

        override fun onComplete() {}

        override fun onError(e: Throwable) {
            if (e is EncryptionKeyNotFoundException) {
                currentEncryptionKey = null
                val loadedKey = getString(R.string.loaded_key_placeholder, "No Key Loaded")
                loaded_key.text = loadedKey
            }
        }
    }

    private fun handleEncryptText() {
        if (currentEncryptionKey == null) {
            Toast.makeText(this, "Key not loaded", Toast.LENGTH_SHORT).show()
        }
        val textToEncrypt = input_text.text.toString()
        currentEncryptionKey?.let {
            val encryptedText = encryptionManager.encryptText(it, textToEncrypt)
            input_text.setText(encryptedText)
            input_text.isEnabled = false
            input_text.setBackgroundResource(R.color.light_black)
            input_text.setTextColor(Color.WHITE)
        }
    }
}
