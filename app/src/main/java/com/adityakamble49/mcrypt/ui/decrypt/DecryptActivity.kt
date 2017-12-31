package com.adityakamble49.mcrypt.ui.decrypt

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.adityakamble49.mcrypt.R
import com.adityakamble49.mcrypt.cache.exception.EncryptionKeyNotFoundException
import com.adityakamble49.mcrypt.model.EncryptionKey
import com.adityakamble49.mcrypt.ui.common.CommonViewModel
import com.adityakamble49.mcrypt.ui.common.CommonViewModelFactory
import com.adityakamble49.mcrypt.ui.keys.KeyManagerActivity
import dagger.android.AndroidInjection
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_decrypt.*
import timber.log.Timber
import javax.inject.Inject

class DecryptActivity : AppCompatActivity(), View.OnClickListener {

    // Dagger Injected Fields
    @Inject lateinit var commonViewModelFactory: CommonViewModelFactory
    @Inject lateinit var decryptViewModelFactory: DecryptViewModelFactory

    // ViewModel
    private lateinit var commonViewModel: CommonViewModel
    private lateinit var decryptViewModel: DecryptViewModel

    // Other Fields
    var currentEncryptionKey: EncryptionKey? = null
    var isDecrypted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_decrypt)

        AndroidInjection.inject(this)

        // Get Key Manager ViewModel from Factory
        commonViewModel = ViewModelProviders.of(this, commonViewModelFactory).get(
                CommonViewModel::class.java)
        decryptViewModel = ViewModelProviders.of(this, decryptViewModelFactory).get(
                DecryptViewModel::class.java)

        bindView()

        // Get intent extras
        handleIntentExtras()
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
            R.id.decrypt_button -> handleDecryptText()
            R.id.change_encryption_key -> startActivity(
                    Intent(this, KeyManagerActivity::class.java))
        }
    }


    /*
     * Helper Functions
     */

    private fun bindView() {
        decrypt_button.setOnClickListener(this)
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

    private fun handleIntentExtras() {
        val uri: Uri? = intent.data
        Timber.i(uri.toString())
        uri?.let {
            decryptViewModel.getEncryptedTextFromFile(uri, GetEncryptedTextFromFileSubscriber())
        }
    }

    private inner class GetEncryptedTextFromFileSubscriber : SingleObserver<String> {
        override fun onSubscribe(d: Disposable) {
        }

        override fun onSuccess(t: String) {
            handleEncryptedTextFromFile(t)
        }

        override fun onError(e: Throwable) {
            Timber.i(e)
        }
    }

    private fun handleEncryptedTextFromFile(encryptedText: String) {
        input_text.setText(encryptedText)
    }

    private fun handleDecryptText() {
        if (isDecrypted) {
            Toast.makeText(this, "Text Already Decrypted", Toast.LENGTH_SHORT).show()
            return
        }
        if (currentEncryptionKey == null) {
            Toast.makeText(this, "Key not loaded", Toast.LENGTH_SHORT).show()
            return
        }
        val textToEncrypt = input_text.text.toString()
        if (textToEncrypt.isEmpty()) {
            Toast.makeText(this, "Text Empty!", Toast.LENGTH_SHORT).show()
            return
        }
        currentEncryptionKey?.let {
            decryptViewModel.decryptText(it, textToEncrypt, DecryptTextSubscriber())
        }
    }

    private inner class DecryptTextSubscriber : SingleObserver<String> {
        override fun onSubscribe(d: Disposable) {}

        override fun onSuccess(decryptedText: String) {
            handleDecryptedText(decryptedText)
            Toast.makeText(this@DecryptActivity, "Decryption Successful", Toast.LENGTH_SHORT).show()
        }

        override fun onError(e: Throwable) {
            Timber.i(e)
            Toast.makeText(this@DecryptActivity, "Decryption Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleDecryptedText(decryptedText: String) {
        isDecrypted = true
        input_text.setText(decryptedText)
        input_text.isEnabled = false
        input_text.setBackgroundResource(R.color.white)
        input_text.setTextColor(ContextCompat.getColor(this, R.color.light_black))
    }
}
