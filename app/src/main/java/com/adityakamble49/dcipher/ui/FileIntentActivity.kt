package com.adityakamble49.dcipher.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.adityakamble49.dcipher.R
import com.adityakamble49.dcipher.interactor.GetFileTypeUseCase
import com.adityakamble49.dcipher.model.FileType
import com.adityakamble49.dcipher.ui.decrypt.DecryptActivity
import com.adityakamble49.dcipher.ui.encrypt.EncryptActivity
import com.adityakamble49.dcipher.ui.keys.KeyManagerActivity
import com.adityakamble49.dcipher.utils.makeVisible
import dagger.android.AndroidInjection
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_file_intent.*
import timber.log.Timber
import javax.inject.Inject

class FileIntentActivity : AppCompatActivity() {

    // Dagger Injected Properties
    @Inject lateinit var getFileTypeUseCase: GetFileTypeUseCase

    // Other Properties
    lateinit var currentUri: Uri


    /*
     * Lifecycle Functions
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_intent)

        AndroidInjection.inject(this)

        handleIntentExtras()
    }


    /*
     * Helper Functions
     */

    private fun handleIntentExtras() {
        val uri: Uri? = intent.data
        Timber.i(uri.toString())
        uri?.let {
            currentUri = it
            getFileTypeUseCase.execute(it).subscribe(GetFileTypeUseCaseSubscriber())
        }
    }

    private inner class GetFileTypeUseCaseSubscriber : SingleObserver<FileType> {
        override fun onSubscribe(d: Disposable) {
        }

        override fun onSuccess(t: FileType) {
            openActivityByType(t)
        }

        override fun onError(e: Throwable) {
            Timber.i(e)
        }

    }

    private fun openActivityByType(fileType: FileType) {
        when (fileType) {
            FileType.EncryptionKey -> startKeyManagerActivity()
            FileType.EncryptedText -> startDecryptActivity()
            FileType.TXT -> startEncryptActivity()
            FileType.UNKNOWN -> showFileUnsupportedMessage()
        }
    }

    private fun startKeyManagerActivity() {
        val keyManagerActivityIntent = Intent(this, KeyManagerActivity::class.java)
        keyManagerActivityIntent.data = currentUri
        startActivity(keyManagerActivityIntent)
        finish()
    }

    private fun startEncryptActivity() {
        val encryptActivityIntent = Intent(this, EncryptActivity::class.java)
        encryptActivityIntent.data = currentUri
        startActivity(encryptActivityIntent)
        finish()
    }

    private fun startDecryptActivity() {
        val decryptActivityIntent = Intent(this, DecryptActivity::class.java)
        decryptActivityIntent.data = currentUri
        startActivity(decryptActivityIntent)
        finish()
    }

    private fun showFileUnsupportedMessage() {
        file_intent_error_view.makeVisible()
    }
}
