package com.adityakamble49.mcrypt.ui.decrypt

import android.Manifest
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
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
import com.adityakamble49.mcrypt.utils.hasSpecialChar
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.folderselector.FolderChooserDialog
import dagger.android.AndroidInjection
import io.reactivex.CompletableObserver
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_decrypt.*
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class DecryptActivity : AppCompatActivity(), View.OnClickListener,
        FolderChooserDialog.FolderCallback {

    // Dagger Injected Fields
    @Inject lateinit var commonViewModelFactory: CommonViewModelFactory
    @Inject lateinit var decryptViewModelFactory: DecryptViewModelFactory

    // ViewModel
    private lateinit var commonViewModel: CommonViewModel
    private lateinit var decryptViewModel: DecryptViewModel

    // Other Fields
    val PERMISSION_WRITE = Manifest.permission.WRITE_EXTERNAL_STORAGE
    val PERMISSION_LIST = arrayOf(PERMISSION_WRITE)
    val REQ_SAVE_DECRYPTED_FILE = 101
    var currentEncryptionKey: EncryptionKey? = null
    var isDecrypted = false
    var currentDecryptedText: String? = null
    var currentDecryptedTextFileName: String? = null

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_decrypt, menu)
        return true
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_reset -> handleResetDecryption()
            R.id.action_save -> handleSaveDecryption()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onFolderSelection(dialog: FolderChooserDialog, folder: File) {
        currentDecryptedText?.let { text ->
            currentDecryptedTextFileName?.let { fileName ->
                decryptViewModel.saveDecryptedText(folder.absolutePath, fileName, text,
                        SaveDecryptedTextToFileSubscriber())
            }
        }

    }

    override fun onFolderChooserDismissed(dialog: FolderChooserDialog) {

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            REQ_SAVE_DECRYPTED_FILE -> handleSaveDecryption()
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
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
        input_text.setBackgroundResource(R.color.almost_white)
        input_text.setTextColor(ContextCompat.getColor(this, R.color.light_black))
    }

    private fun handleResetDecryption() {
        isDecrypted = false
        input_text.setText("")
        input_text.isEnabled = true
        input_text.setBackgroundResource(R.color.almost_black)
        input_text.setTextColor(ContextCompat.getColor(this, R.color.white))
    }

    private fun handleSaveDecryption() {
        if (!isDecrypted) {
            Toast.makeText(this, "Text not decrypted", Toast.LENGTH_LONG).show()
            return
        }
        performExternalStorageOperation(REQ_SAVE_DECRYPTED_FILE, { saveDecryptedTextToFile() })
    }

    private fun saveDecryptedTextToFile() {
        currentDecryptedText = input_text.text.toString()
        buildSaveDecryptedFileNameDialog().show()
    }

    private fun buildSaveDecryptedFileNameDialog() = MaterialDialog
            .Builder(this)
            .title(getString(R.string.decrypted_file_dialog_title))
            .content(getString(R.string.decrypted_file_dialog_content))
            .inputType(InputType.TYPE_CLASS_TEXT)
            .inputRange(1, 50)
            .input(getString(R.string.decrypted_file_dialog_hint),
                    getString(R.string.decrypted_file_dialog_prefill), { dialog, input ->
                val keyName = input.toString()
                if (keyName.hasSpecialChar()) {
                    dialog.getActionButton(DialogAction.POSITIVE).isEnabled = false
                    dialog.setContent(
                            getString(R.string.decrypted_file_dialog_special_char_warning))
                } else {
                    dialog.getActionButton(DialogAction.POSITIVE).isEnabled = true
                    dialog.setContent(getString(R.string.decrypted_file_dialog_content))
                }
            })
            .onPositive { dialog, _ ->
                dialog.inputEditText?.text.let {
                    currentDecryptedTextFileName = it.toString()
                    buildSaveDecryptedFileDirDialog().show(this)
                }
            }
            .alwaysCallInputCallback()
            .build()

    private fun buildSaveDecryptedFileDirDialog() = FolderChooserDialog
            .Builder(this)
            .initialPath(Environment.getExternalStorageDirectory().absolutePath)
            .build()

    private fun performExternalStorageOperation(permissionReqCode: Int, operation: () -> Unit) {
        if (ContextCompat.checkSelfPermission(this,
                PERMISSION_WRITE) != PackageManager.PERMISSION_GRANTED) {
            // Request permission
            ActivityCompat.requestPermissions(this, PERMISSION_LIST, permissionReqCode)
        } else {
            // Perform operation
            operation()
        }
    }

    private inner class SaveDecryptedTextToFileSubscriber : CompletableObserver {
        override fun onSubscribe(d: Disposable) {}

        override fun onComplete() {
            Toast.makeText(this@DecryptActivity, "Text Saved", Toast.LENGTH_SHORT).show()
        }

        override fun onError(e: Throwable) {
            Timber.e(e)
            Toast.makeText(this@DecryptActivity, "Text save failed", Toast.LENGTH_SHORT).show()
        }
    }
}
