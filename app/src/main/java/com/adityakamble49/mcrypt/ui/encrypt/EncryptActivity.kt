package com.adityakamble49.mcrypt.ui.encrypt

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.adityakamble49.mcrypt.R
import com.adityakamble49.mcrypt.cache.exception.EncryptionKeyNotFoundException
import com.adityakamble49.mcrypt.model.EncryptionKey
import com.adityakamble49.mcrypt.ui.common.CommonViewModel
import com.adityakamble49.mcrypt.ui.common.CommonViewModelFactory
import com.adityakamble49.mcrypt.ui.keys.KeyManagerActivity
import com.adityakamble49.mcrypt.utils.Constants.Companion.TEXT_INTENT
import com.adityakamble49.mcrypt.utils.Constants.ShareEncryptionType
import com.adityakamble49.mcrypt.utils.hasSpecialChar
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import dagger.android.AndroidInjection
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_encrypt.*
import timber.log.Timber
import javax.inject.Inject

class EncryptActivity : AppCompatActivity(), View.OnClickListener {

    // Dagger Injected Fields
    @Inject lateinit var commonViewModelFactory: CommonViewModelFactory
    @Inject lateinit var encryptViewModelFactory: EncryptViewModelFactory

    // ViewModel
    private lateinit var commonViewModel: CommonViewModel
    private lateinit var encryptViewModel: EncryptViewModel

    // Other Fields
    var currentEncryptionKey: EncryptionKey? = null
    var isEncrypted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encrypt)

        AndroidInjection.inject(this)

        // Get Key Manager ViewModel from Factory
        commonViewModel = ViewModelProviders.of(this, commonViewModelFactory).get(
                CommonViewModel::class.java)
        encryptViewModel = ViewModelProviders.of(this, encryptViewModelFactory).get(
                EncryptViewModel::class.java)

        bindView()
    }

    override fun onResume() {
        super.onResume()
        commonViewModel.requestCurrentEncryptionKey(GetCurrentEncryptionKeySubscriber())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_encrypt, menu)
        return true
    }

    /*
     * Listener Functions
     */

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.encrypt_button -> handleEncryptText()
            R.id.change_encryption_key -> startActivity(
                    Intent(this, KeyManagerActivity::class.java))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_reset -> handleResetEncryption()
            R.id.action_share -> handleShareEncryption()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
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
        if (isEncrypted) {
            Toast.makeText(this, "Text Already Encrypted", Toast.LENGTH_SHORT).show()
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
            encryptViewModel.encryptText(it, textToEncrypt, EncryptTextSubscriber())
        }
    }

    private inner class EncryptTextSubscriber : SingleObserver<String> {
        override fun onSubscribe(d: Disposable) {}

        override fun onSuccess(encryptedText: String) {
            handleEncryptedText(encryptedText)
            Toast.makeText(this@EncryptActivity, "Encryption Successful", Toast.LENGTH_SHORT).show()
        }

        override fun onError(e: Throwable) {
            Toast.makeText(this@EncryptActivity, "Encryption Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleEncryptedText(encryptedText: String) {
        isEncrypted = true
        input_text.setText(encryptedText)
        input_text.isEnabled = false
        input_text.setBackgroundResource(R.color.light_black)
        input_text.setTextColor(ContextCompat.getColor(this, R.color.white))
    }

    private fun handleResetEncryption() {
        isEncrypted = false
        input_text.setText("")
        input_text.isEnabled = true
        input_text.setBackgroundResource(R.color.white)
        input_text.setTextColor(ContextCompat.getColor(this, R.color.almost_black))
    }

    private fun handleShareEncryption() {
        if (!isEncrypted) {
            Toast.makeText(this, "Text not encrypted", Toast.LENGTH_SHORT).show()
            return
        }
        buildShareWithDialog().show()
    }

    private fun buildShareWithDialog() = MaterialDialog.Builder(this)
            .title("Share encrypted text with")
            .items(R.array.share_with_options)
            .itemsCallback { _, _, position, _ -> handleShareEncryptionWith(position) }
            .build()

    private fun handleShareEncryptionWith(shareWith: Int) {
        val encryptedText = input_text.text.toString()
        when (shareWith) {
            ShareEncryptionType.TEXT -> {
                val shareTextIntent = Intent(Intent.ACTION_SEND)
                shareTextIntent.type = TEXT_INTENT
                shareTextIntent.putExtra(Intent.EXTRA_TEXT, encryptedText)
                startActivity(Intent.createChooser(shareTextIntent, getString(R.string.share)))
            }
            ShareEncryptionType.FILE -> {
                buildShareEncryptedFileNameDialog(encryptedText).show()
            }
        }
    }

    private fun buildShareEncryptedFileNameDialog(
            encryptedText: String): MaterialDialog = MaterialDialog.Builder(this)
            .title(getString(R.string.encrypted_file_dialog_title))
            .content(getString(R.string.encrypted_file_dialog_content))
            .inputType(InputType.TYPE_CLASS_TEXT)
            .inputRange(1, 50)
            .input(getString(R.string.encrypted_file_dialog_hint),
                    getString(R.string.encrypted_file_dialog_prefill), { dialog, input ->
                val keyName = input.toString()
                if (keyName.hasSpecialChar()) {
                    dialog.getActionButton(DialogAction.POSITIVE).isEnabled = false
                    dialog.setContent(
                            getString(R.string.encrypted_file_dialog_special_char_warning))
                } else {
                    dialog.getActionButton(DialogAction.POSITIVE).isEnabled = true
                    dialog.setContent(getString(R.string.encrypted_file_dialog_content))
                }
            })
            .onPositive { dialog, _ ->
                dialog.inputEditText?.text.let {
                    encryptViewModel.saveEncryptedTextToFile(it.toString(), encryptedText,
                            SaveEncryptedTextToFileSubscriber())
                }
            }
            .alwaysCallInputCallback()
            .build()

    private inner class SaveEncryptedTextToFileSubscriber : SingleObserver<Uri> {
        override fun onSubscribe(d: Disposable) {}

        override fun onSuccess(t: Uri) {
            createShareEncryptedFileIntent(t)
        }

        override fun onError(e: Throwable) {
            Timber.i(e)
        }
    }

    private fun createShareEncryptedFileIntent(fileUri: Uri) {
        val shareKeyIntent = Intent()
        shareKeyIntent.action = Intent.ACTION_SEND
        shareKeyIntent.putExtra(Intent.EXTRA_STREAM, fileUri)
        shareKeyIntent.type = "*/*"
        startActivity(Intent.createChooser(shareKeyIntent, "Share Encrypted file"))
    }
}
