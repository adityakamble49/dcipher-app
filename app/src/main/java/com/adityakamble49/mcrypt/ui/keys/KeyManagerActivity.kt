package com.adityakamble49.mcrypt.ui.keys

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.text.InputType
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.adityakamble49.mcrypt.AppExecutors
import com.adityakamble49.mcrypt.R
import com.adityakamble49.mcrypt.cache.PreferenceHelper
import com.adityakamble49.mcrypt.model.RSAKeyPair
import com.adityakamble49.mcrypt.utils.updateUI
import com.afollestad.materialdialogs.MaterialDialog
import dagger.android.AndroidInjection
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_key_manager.*
import timber.log.Timber
import javax.inject.Inject

/**
 * RSA Key Pair - Manager Activity
 *
 * @author Aditya Kamble
 * @since 10/12/2017
 */
class KeyManagerActivity : AppCompatActivity(), AdapterView.OnItemClickListener,
        View.OnClickListener {

    // Dagger Injected Fields
    @Inject lateinit var appExecutors: AppExecutors
    @Inject lateinit var keyManagerViewModelFactory: KeyManagerViewModelFactory
    @Inject lateinit var preferenceHelper: PreferenceHelper

    // ViewModel
    private lateinit var keyManagerViewModel: KeyManagerViewModel

    // Views
    private lateinit var rsaKeyListAdapter: RSAKeyListAdapter

    /*
     * Lifecycle Functions
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_key_manager)

        AndroidInjection.inject(this)

        // Get Key Manager ViewModel from Factory
        keyManagerViewModel = ViewModelProviders.of(this, keyManagerViewModelFactory).get(
                KeyManagerViewModel::class.java)

        bindView()

        // Observe RSA Key List
        observeRSAKeyPairList()
    }


    /*
     * Listener Functions
     */

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.import_key_fab -> Timber.i("Import Key")
            R.id.generate_key_fab -> handleGenerateKey()
        }
        if (key_manager_fab.isExpanded) key_manager_fab.collapse()
    }

    override fun onItemClick(adapterView: AdapterView<*>?, view: View, position: Int, id: Long) {
        showRSAKeyPairOptionMenu(view, position)
    }


    /*
     * Helper Functions
     */

    private fun bindView() {

        // Setup Support Action Bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Setup RSA Key List RecyclerView
        val linearLayoutManager = LinearLayoutManager(this)
        val decorator = DividerItemDecoration(this, linearLayoutManager.orientation)
        rsaKeyListAdapter = RSAKeyListAdapter()
        rsaKeyListAdapter.onItemClickListener = this
        rsa_key_list.layoutManager = linearLayoutManager
        rsa_key_list.addItemDecoration(decorator)
        rsa_key_list.adapter = rsaKeyListAdapter

        // Setup Key Manager FAB
        import_key_fab.setOnClickListener(this)
        generate_key_fab.setOnClickListener(this)
    }

    private fun showRSAKeyPairOptionMenu(view: View, position: Int) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.menu_rsa_key_options, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_use -> {
                    val currentKey = rsaKeyListAdapter.rsaKeyPairList[position]
                    preferenceHelper.currentRSAKeyId = currentKey.id
                    Toast.makeText(this@KeyManagerActivity, "Key changed to ${currentKey.name}",
                            Toast.LENGTH_SHORT).show()
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    private fun observeRSAKeyPairList() {
        keyManagerViewModel.rsaKeyPairList.observe(this, Observer<List<RSAKeyPair>> {
            it?.let {
                appExecutors.updateUI {
                    rsaKeyListAdapter.rsaKeyPairList = it
                    rsaKeyListAdapter.notifyDataSetChanged()
                }
            }
        })
    }

    private fun handleGenerateKey() {
        buildGenerateKeyDialog().show()
    }

    private fun buildGenerateKeyDialog(): MaterialDialog = MaterialDialog.Builder(this)
            .title(getString(R.string.generate_key_dialog_title))
            .content(getString(R.string.generate_key_dialog_content))
            .inputType(InputType.TYPE_CLASS_TEXT)
            .input(getString(R.string.generate_key_dialog_hint),
                    getString(R.string.generate_key_dialog_prefill), { _, input ->
                val keyName = input.toString()
                keyManagerViewModel.generateAndSaveKeyPair(keyName, SaveKeyPairSubscriber())
            }).build()

    private inner class SaveKeyPairSubscriber : CompletableObserver {
        override fun onComplete() {
            Toast.makeText(this@KeyManagerActivity, getString(R.string.rsa_key_generation_success),
                    Toast.LENGTH_SHORT).show()
        }

        override fun onSubscribe(d: Disposable) {}

        override fun onError(e: Throwable) {
            Toast.makeText(this@KeyManagerActivity,
                    getString(R.string.rsa_key_generation_failed_name_present),
                    Toast.LENGTH_SHORT).show()
        }
    }
}